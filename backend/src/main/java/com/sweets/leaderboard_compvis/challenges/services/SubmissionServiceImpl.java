package com.sweets.leaderboard_compvis.challenges.services;

import com.sweets.leaderboard_compvis.challenges.exceptions.ChallengeNotFoundException;
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
    private final SubmissionMapper submissionMapper;

    @Autowired
    public SubmissionServiceImpl(SubmissionMetadataRepository submissionMetadataRepository,
                                 SubmissionMapper submissionMapper,
                                 S3Repository s3Repository, ChallengeRepository challengeRepository) {
        this.submissionMetadataRepository = submissionMetadataRepository;
        this.submissionMapper = submissionMapper;
        this.s3Repository = s3Repository;
        this.challengeRepository = challengeRepository;
    }

    @Override
    public SubmissionStatusDto approveSubmission(UUID submissionId, SubmissionApproveDto approveDto) {

        if (submissionId == null) {
            throw new IllegalArgumentException("Submission id cannot be null");
        }

        if (approveDto == null) {
            throw new BadRequestException("Max precision, max recall and split are required");
        }

        SubmissionMetadata submission = submissionMetadataRepository.findByAttachmentId(submissionId)
                .orElseThrow(() -> new SubmissionNotFoundException(submissionId));

        ESubmissionStatus status = ESubmissionStatus.APPROVED;

        submission.setSubmissionStatus(status);

        submission.setMaxPrecision(approveDto.getMaxPrecision());
        submission.setMaxRecall(approveDto.getMaxRecall());
        submission.setSplit(approveDto.getSplit());

        submissionMetadataRepository.save(submission);

        return new SubmissionStatusDto(submissionId, status.name());
    }

    @Override
    public SubmissionStatusDto rejectSubmission(UUID submissionId) {
        return setSubmissionStatus(submissionId, ESubmissionStatus.REJECTED);
    }

    @Override
    public SubmissionDto uploadChallengeSubmission(Long challengeId, ChallengeSubmitUploadDto uploadDto,
                                                   MultipartFile file) throws IOException {
        if (challengeId == null) {
            throw new BadRequestException("Challenge ID cannot be null");
        }

        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new ChallengeNotFoundException(challengeId));

        if (submissionMetadataRepository.existsByChallengeIdAndSubmitterEmail(challengeId, uploadDto.getEmail())) {
            throw new SubmissionAlreadyExistsException(challengeId, uploadDto.getEmail());
        }

        UUID attachmentId = UUID.randomUUID();

        //make filepath to store csv
        String fileName = StringUtils.strip(file.getOriginalFilename());
        String filepath = String.format("%d/%s/%s", challengeId, attachmentId, fileName);

        //save to s3
        saveCsv(submissionsBucket, filepath, file);

        SubmissionMetadata metadata = new SubmissionMetadata(attachmentId, filepath, fileName, EMimeTypes.textCsv,
                file.getSize(), challenge, uploadDto.getFirstName(), uploadDto.getLastName(), uploadDto.getEmail(),
                ESubmissionStatus.PENDING);

        submissionMetadataRepository.save(metadata);

        challenge.addSubmission(metadata);

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
    public PagedResponse<SubmissionLeaderboardDto> getSubmissionsByChallengeIdPaged(Long challengeId,
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

    public List<SubmissionListItemDto> getChallengeSubmissionsForModeration(SubmissionFilterDto filter,
                                                                            Pageable pageable) {
        Specification<SubmissionMetadata> spec = SubmissionSpecifications.fromFilter(filter);

        Page<SubmissionMetadata> page = submissionMetadataRepository.findAll(spec, pageable);

        return submissionMapper.toSubmissionListItemDtoList(page.getContent());
    }

    private String saveCsv(String bucket, String filepath, MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            long contentLength = file.getSize();

            return s3Repository.save(bucket, filepath, inputStream, contentLength, EMimeTypes.textCsv);
        }
    }

    private SubmissionStatusDto setSubmissionStatus(UUID submissionId, ESubmissionStatus submissionStatus) {
        if (submissionId == null) {
            throw new IllegalArgumentException("Submission id cannot be null");
        }

        SubmissionMetadata submission = submissionMetadataRepository.findByAttachmentId(submissionId)
                .orElseThrow(() -> new SubmissionNotFoundException(submissionId));

        submission.setSubmissionStatus(submissionStatus);

        submissionMetadataRepository.save(submission);

        return new SubmissionStatusDto(submissionId, submissionStatus.name());
    }
}
