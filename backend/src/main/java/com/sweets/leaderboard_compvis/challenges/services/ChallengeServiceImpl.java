package com.sweets.leaderboard_compvis.challenges.services;

import com.sweets.leaderboard_compvis.challenges.exceptions.ChallengeNotFoundException;
import com.sweets.leaderboard_compvis.challenges.models.DTO.ChallengeDto;
import com.sweets.leaderboard_compvis.challenges.models.DTO.CreateChallengeDto;
import com.sweets.leaderboard_compvis.challenges.models.DTO.DatasetDownloadDto;
import com.sweets.leaderboard_compvis.challenges.models.DTO.FileDownloadDto;
import com.sweets.leaderboard_compvis.challenges.models.EMimeTypes;
import com.sweets.leaderboard_compvis.challenges.models.JPA.Challenge;
import com.sweets.leaderboard_compvis.challenges.models.JPA.DatasetMetadata;
import com.sweets.leaderboard_compvis.challenges.models.mapper.ChallengeMapper;
import com.sweets.leaderboard_compvis.challenges.repositories.ChallengeRepository;
import com.sweets.leaderboard_compvis.challenges.repositories.DatasetMetadataRepository;
import com.sweets.leaderboard_compvis.challenges.repositories.S3Repository;
import com.sweets.leaderboard_compvis.challenges.repositories.SubmissionMetadataRepository;
import com.sweets.leaderboard_compvis.infrastructure.exceptions.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    @Value("${aws.s3.attachments-bucket-name}")
    private String attachmentBucket;

    @Value("${aws.s3.submissions-bucket-name}")
    private String submissionsBucket;

    private final ChallengeRepository challengeRepository;
    private final S3Repository s3Repository;
    private final DatasetMetadataRepository datasetMetadataRepository;
    private final SubmissionMetadataRepository submissionMetadataRepository;
    private final ChallengeMapper mapper;

    @Autowired
    public ChallengeServiceImpl(ChallengeRepository challengeRepository,
                                S3Repository s3Repository,
                                DatasetMetadataRepository datasetMetadataRepository,
                                SubmissionMetadataRepository submissionMetadataRepository,
                                ChallengeMapper challengeMapper) {
        this.challengeRepository = challengeRepository;
        this.s3Repository = s3Repository;
        this.datasetMetadataRepository = datasetMetadataRepository;
        this.submissionMetadataRepository = submissionMetadataRepository;
        this.mapper = challengeMapper;
    }

    @Override
    public List<ChallengeDto> getChallengesPaged(Pageable pageable) {
        Page<Challenge> page = challengeRepository.findAll(pageable);

        List<ChallengeDto> challenges = mapper.toCreateChallengeDto(page.getContent());

        return challenges;
    }

    @Override
    public void createChallenge(CreateChallengeDto dto) {
        Challenge challenge = mapper.toEntity(dto);

        //challenge.setCreatedAt(OffsetDateTime.now());
        challenge.setActive(true);

        challengeRepository.save(challenge);
    }

    @Override
    public ChallengeDto getChallengeById(Long challengeId) {

        if (challengeId == null) {
            throw new BadRequestException("Challenge ID cannot be null");
        }

        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new ChallengeNotFoundException(challengeId));

        return mapper.toChallengeDto(challenge);
    }

    @Override
    public void uploadDataset(Long challengeId, MultipartFile file) throws IOException {

        if (challengeId == null) {
            throw new BadRequestException("Challenge ID cannot be null");
        }

        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new ChallengeNotFoundException(challengeId));

        UUID attachmentId = UUID.randomUUID();

        //make filepath to store csv
        String fileName = StringUtils.strip(file.getOriginalFilename());
        String filepath = String.format("%d/%s/%s", challengeId, attachmentId, fileName);

        //save to s3
        saveCsv(attachmentBucket, filepath, file);

        //save metadata
        DatasetMetadata metadata = new DatasetMetadata(attachmentId, filepath, fileName, EMimeTypes.textCsv,
                file.getSize());
        datasetMetadataRepository.save(metadata);

        //link to challenge
        challenge.addDataset(metadata);
        challengeRepository.save(challenge);
    }

    @Override
    public FileDownloadDto downloadDataset(UUID attachmentId) throws NoSuchElementException {
        DatasetMetadata metadata = datasetMetadataRepository.findByAttachmentId(attachmentId)
                .orElseThrow(NoSuchElementException::new);

        InputStream inputStream = s3Repository.getFileInputStream(attachmentBucket,
                metadata.getStorageKey());

        return new FileDownloadDto(inputStream, metadata.getFileName(), metadata.getContentType(),
                metadata.getContentLength());
    }

    @Override
    public List<DatasetDownloadDto> getDatasetsByChallengeIdPaged(Long challengeId, Pageable pageable) {

        if (challengeId == null) {
            throw new BadRequestException("Challenge ID cannot be null");
        }

        if (!challengeRepository.existsById(challengeId)) {
            throw new ChallengeNotFoundException(challengeId);
        }

        Page<DatasetMetadata> page = challengeRepository.findDatasetsByChallengeId(challengeId, pageable);

        return mapper.toDatasetDownloadDtoList(page.getContent());
    }

    private String saveCsv(String bucket, String filepath, MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            long contentLength = file.getSize();

            return s3Repository.save(bucket, filepath, inputStream, contentLength, EMimeTypes.textCsv);
        }
    }
}
