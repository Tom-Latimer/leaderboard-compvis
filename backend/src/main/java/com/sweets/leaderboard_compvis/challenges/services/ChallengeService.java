package com.sweets.leaderboard_compvis.challenges.services;

import com.sweets.leaderboard_compvis.challenges.models.DTO.*;
import com.sweets.leaderboard_compvis.infrastructure.models.DTO.PagedResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.UUID;

public interface ChallengeService {

    PagedResponse<ChallengeDto> getChallengesPaged(Pageable pageable);

    @Transactional
    @PreAuthorize("@auth.hasRole('ADMIN')")
    void createChallenge(CreateChallengeDto dto);

    ChallengeDto getChallengeById(Long id);

    ChallengeOverviewDto getChallengeOverview(Long id);

    @Transactional
    @PreAuthorize("@auth.hasRole('ADMIN')")
    void uploadDataset(Long challengeId, MultipartFile file) throws IOException;

    FileDownloadDto downloadDataset(UUID attachmentId) throws NoSuchElementException;

    PagedResponse<DatasetDownloadDto> getDatasetsByChallengeIdPaged(Long challengeId, Pageable pageable);
}