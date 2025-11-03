package com.sweets.leaderboard_compvis.challenges.services;

import com.sweets.leaderboard_compvis.challenges.models.DTO.ChallengeDto;
import com.sweets.leaderboard_compvis.challenges.models.DTO.CreateChallengeDto;
import com.sweets.leaderboard_compvis.challenges.models.DTO.DatasetDownloadDto;
import com.sweets.leaderboard_compvis.challenges.models.DTO.FileDownloadDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public interface ChallengeService {

    List<ChallengeDto> getChallengesPaged(Pageable pageable);

    @Transactional
    @PreAuthorize("@auth.hasRole('ADMIN')")
    void createChallenge(CreateChallengeDto dto);

    ChallengeDto getChallengeById(Long id);

    @Transactional
    @PreAuthorize("@auth.hasRole('ADMIN')")
    void uploadDataset(Long challengeId, MultipartFile file) throws IOException;

    FileDownloadDto downloadDataset(UUID attachmentId) throws NoSuchElementException;

    List<DatasetDownloadDto> getDatasetsByChallengeIdPaged(Long challengeId, Pageable pageable);
}