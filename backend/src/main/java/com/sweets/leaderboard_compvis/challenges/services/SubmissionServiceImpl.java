package com.sweets.leaderboard_compvis.challenges.services;

import com.sweets.leaderboard_compvis.challenges.exceptions.ChallengeNotFoundException;
import com.sweets.leaderboard_compvis.challenges.exceptions.ESubmissionErrorCodes;
import com.sweets.leaderboard_compvis.challenges.exceptions.SubmissionAlreadyExistsException;
import com.sweets.leaderboard_compvis.challenges.exceptions.SubmissionNotFoundException;
import com.sweets.leaderboard_compvis.challenges.models.DTO.FileDownloadDto;
import com.sweets.leaderboard_compvis.challenges.models.EMimeTypes;
import com.sweets.leaderboard_compvis.challenges.models.ESubmissionStatus;
import com.sweets.leaderboard_compvis.challenges.models.JPA.Challenge;
import com.sweets.leaderboard_compvis.challenges.models.JPA.SubmissionMetadata;
import com.sweets.leaderboard_compvis.challenges.models.mapper.SubmissionMapper;
import com.sweets.leaderboard_compvis.challenges.models.submissions.DTO.*;
import com.sweets.leaderboard_compvis.challenges.repositories.ChallengeRepository;
import com.sweets.leaderboard_compvis.challenges.repositories.S3Repository;
import com.sweets.leaderboard_compvis.challenges.repositories.SubmissionMetadataRepository;
import com.sweets.leaderboard_compvis.challenges.repositories.SubmissionTeamMemberRepository;
import com.sweets.leaderboard_compvis.challenges.repositories.specifications.SubmissionSpecifications;
import com.sweets.leaderboard_compvis.infrastructure.exceptions.BadRequestException;
import com.sweets.leaderboard_compvis.infrastructure.models.DTO.PagedResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    @Value("${aws.s3.submissions-bucket-name}")
    private String submissionsBucket;

    private final ChallengeRepository challengeRepository;
    private final S3Repository s3Repository;
    private final SubmissionMetadataRepository submissionMetadataRepository;
    private final SubmissionTeamMemberRepository submissionTeamMemberRepository;
    private final SubmissionMapper submissionMapper;

    @Autowired
    public SubmissionServiceImpl(SubmissionMetadataRepository submissionMetadataRepository,
                                 SubmissionTeamMemberRepository submissionTeamMemberRepository,
                                 SubmissionMapper submissionMapper,
                                 S3Repository s3Repository, ChallengeRepository challengeRepository) {
        this.submissionMetadataRepository = submissionMetadataRepository;
        this.submissionTeamMemberRepository = submissionTeamMemberRepository;
        this.submissionMapper = submissionMapper;
        this.s3Repository = s3Repository;
        this.challengeRepository = challengeRepository;
    }

    @Override
    public SubmissionStatusDto updateStatus(UUID submissionId, SubmissionStatusUpdateDto dto) {

        if (submissionId == null) {
            throw new IllegalArgumentException("Submission id cannot be null");
        }

        if (dto == null) {
            throw new BadRequestException("Missing one or more of the following: Status, Max precision, Max recall or" +
                    " Split");
        }

        //validation the fields for each status type
        if (dto.getStatus() == ESubmissionStatus.APPROVED) {
            if (dto.getMaxPrecision() == null) {
                throw new BadRequestException("Max precision is required for submission approval");
            } else if (dto.getMaxPrecision() < 0 || dto.getMaxPrecision() > 1) {
                throw new BadRequestException("Max precision must be between 0 and 1");
            }

            if (dto.getMaxRecall() == null) {
                throw new BadRequestException("Max recall is required for submission approval");
            } else if (dto.getMaxRecall() < 0 || dto.getMaxRecall() > 1) {
                throw new BadRequestException("Max recall must be between 0 and 1");
            }

            if (dto.getSplit() == null) {
                throw new BadRequestException("Split is required for submission approval");
            } else if (dto.getSplit() < 0 || dto.getSplit() > 1) {
                throw new BadRequestException("Split must be between 0 and 1");
            }

        } else if (dto.getStatus() != ESubmissionStatus.REJECTED || dto.getStatus() != ESubmissionStatus.PENDING) {
            throw new BadRequestException("Submission status is of unknown type");
        }

        SubmissionMetadata submission = submissionMetadataRepository.findByAttachmentId(submissionId)
                .orElseThrow(() -> new SubmissionNotFoundException(submissionId));

        ESubmissionStatus status = dto.getStatus();

        submission.setSubmissionStatus(status);

        //add fields required for approval
        if (status == ESubmissionStatus.APPROVED) {
            submission.setMaxPrecision(dto.getMaxPrecision());
            submission.setMaxRecall(dto.getMaxRecall());
            submission.setSplit(dto.getSplit());
        }

        submissionMetadataRepository.save(submission);

        return new SubmissionStatusDto(submissionId, status.name());
    }

    @Override
    public SubmissionDto uploadChallengeSubmission(Long challengeId, ChallengeSubmitUploadDto uploadDto,
                                                   MultipartFile file) throws IOException {
        if (challengeId == null) {
            throw new BadRequestException("Challenge ID cannot be null");
        }

        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new ChallengeNotFoundException(challengeId));

        //check if team name has been used for the challenge already
        if (submissionMetadataRepository.existsByChallengeIdAndTeamName(challengeId, uploadDto.getTeamName())) {
            throw new SubmissionAlreadyExistsException(String.format("Submission already exists for challenge %d with" +
                    " team name %s", challengeId, uploadDto.getTeamName()),
                    ESubmissionErrorCodes.TeamNameAlreadyExists);
        }

        if (uploadDto.getTeamMembers() == null || uploadDto.getTeamMembers().isEmpty()) {
            throw new BadRequestException("Team members cannot be empty");
        }

        //get the email address of the team's primary contact
        String contactEmail = uploadDto.getTeamMembers().stream()
                .filter(member -> member.getIsContact() != null && member.getIsContact())
                .findFirst()
                .orElseThrow(() -> new BadRequestException("No primary contact for team found"))
                .getEmail();

        //check if primary contact has been used for another submission for the challenge
        if (submissionTeamMemberRepository.isContactEmailTakenForChallenge(challengeId, contactEmail)) {
            throw new SubmissionAlreadyExistsException(String.format("Submission already exists for challenge %d with" +
                    " primary contact email %s", challengeId, contactEmail),
                    ESubmissionErrorCodes.ContactEmailAlreadyExists);
        }

        UUID attachmentId = UUID.randomUUID();

        //make filepath to store csv
        String fileName = StringUtils.strip(file.getOriginalFilename());
        String filePath = String.format("%d/%s/%s", challengeId, attachmentId, fileName);

        //save to s3
        saveCsv(submissionsBucket, filePath, file);

        SubmissionMetadata metadata = submissionMapper.toSubmissionMetadata(uploadDto, challenge, attachmentId,
                filePath, fileName, EMimeTypes.textCsv, file.getSize(), ESubmissionStatus.PENDING);

        submissionMetadataRepository.save(metadata);

        return submissionMapper.toSubmissionDto(metadata);
    }

    @Override
    public FileDownloadDto downloadSubmission(UUID submissionId) {
        SubmissionMetadata metadata = submissionMetadataRepository.findByAttachmentId(submissionId)
                .orElseThrow(() -> new SubmissionNotFoundException(submissionId));

        InputStream inputStream = s3Repository.getFileInputStream(submissionsBucket,
                metadata.getStorageKey());

        return new FileDownloadDto(inputStream, metadata.getFileName(), metadata.getContentType(),
                metadata.getContentLength());
    }

    @Override
    public PagedResponse<SubmissionLeaderboardDto> getSubmissionsByChallengeIdPaged(
            Long challengeId,
            ESubmissionStatus status,
            Pageable pageable) {

        if (challengeId == null) {
            throw new BadRequestException("Challenge ID cannot be null");
        }

        if (!challengeRepository.existsById(challengeId)) {
            throw new ChallengeNotFoundException(challengeId);
        }

        Specification<SubmissionMetadata> spec = SubmissionSpecifications.statusEquals(status)
                .and(SubmissionSpecifications.challengeIdEquals(challengeId));

        Page<SubmissionMetadata> page = submissionMetadataRepository.findAll(spec, pageable);

        final long baseRank = (long) page.getNumber() * page.getSize();

        List<SubmissionLeaderboardDto> items = IntStream.range(0, page.getContent().size())
                .mapToObj(i -> {
                    //get entity and map into dto
                    SubmissionMetadata entity = page.getContent().get(i);
                    SubmissionLeaderboardDto item = submissionMapper.toSubmissionLeaderboardDto(entity);

                    //assign rank according to page number
                    item.setRank(baseRank + i);

                    return item;
                }).collect(Collectors.toList());

        //response type is used for client side pagination
        return new PagedResponse<>(
                items,
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize()
        );
    }

    @Override
    public PagedResponse<SubmissionListItemDto> getChallengeSubmissionsForModeration(SubmissionFilterDto filter,
                                                                                     Pageable pageable) {
        Specification<SubmissionMetadata> spec = SubmissionSpecifications.fromFilter(filter);

        Page<SubmissionMetadata> page = submissionMetadataRepository.findAll(spec, pageable);

        final long baseRank = (long) page.getNumber() * page.getSize();

        List<SubmissionListItemDto> items = IntStream.range(0, page.getContent().size())
                .mapToObj(i -> {
                    //get entity and map into dto
                    SubmissionMetadata entity = page.getContent().get(i);
                    SubmissionListItemDto item = submissionMapper.toSubmissionListItemDto(entity);

                    //assign rank according to page number
                    item.setRank(baseRank + i);

                    return item;
                }).collect(Collectors.toList());

        //response type is used for client side pagination
        return new PagedResponse<>(
                items,
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize()
        );
    }

    @Override
    public SubmissionLeaderboardDetailsDto getSubmissionDetailsForLeaderboard(UUID submissionId) {
        if (submissionId == null) {
            throw new BadRequestException("Submission ID cannot be null");
        }

        SubmissionMetadata metadata = submissionMetadataRepository.findByAttachmentId(submissionId)
                .orElseThrow(() -> new SubmissionNotFoundException(submissionId));

        return submissionMapper.toSubmissionLeaderboardDetailsDto(metadata);
    }

    @Override
    public SubmissionDetailsDto getSubmissionDetails(UUID submissionId) {
        if (submissionId == null) {
            throw new BadRequestException("Submission ID cannot be null");
        }

        SubmissionMetadata metadata = submissionMetadataRepository.findByAttachmentId(submissionId)
                .orElseThrow(() -> new SubmissionNotFoundException(submissionId));

        return submissionMapper.toSubmissionDetailsDto(metadata);
    }

    private String saveCsv(String bucket, String filepath, MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            long contentLength = file.getSize();

            return s3Repository.save(bucket, filepath, inputStream, contentLength, EMimeTypes.textCsv);
        }
    }
}
