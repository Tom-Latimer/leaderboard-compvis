package com.sweets.leaderboard_compvis.challenges.services;

import com.sweets.leaderboard_compvis.challenges.models.DTO.FileDownloadDto;
import com.sweets.leaderboard_compvis.challenges.models.ESubmissionStatus;
import com.sweets.leaderboard_compvis.challenges.models.submissions.DTO.*;
import com.sweets.leaderboard_compvis.infrastructure.models.DTO.PagedResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface SubmissionService {
    @Transactional
    @PreAuthorize("@auth.hasAnyRole('ADMIN', 'MODERATOR')")
    SubmissionStatusDto approveSubmission(UUID submissionId, SubmissionApproveDto approveDto);

    @Transactional
    @PreAuthorize("@auth.hasAnyRole('ADMIN', 'MODERATOR')")
    SubmissionStatusDto rejectSubmission(UUID submissionId);

    @Transactional
    SubmissionDto uploadChallengeSubmission(Long challengeId, ChallengeSubmitUploadDto uploadDto, MultipartFile file) throws IOException;

    @PreAuthorize("@auth.hasAnyRole('ADMIN', 'MODERATOR')")
    FileDownloadDto downloadSubmission(UUID submissionId);

    PagedResponse<SubmissionLeaderboardDto> getSubmissionsByChallengeIdPaged(Long challengeId,
                                                                             ESubmissionStatus status,
                                                                             Pageable pageable);

    @PreAuthorize("@auth.hasAnyRole('ADMIN', 'MODERATOR')")
    List<SubmissionListItemDto> getChallengeSubmissionsForModeration(SubmissionFilterDto filter,
                                                                     Pageable pageable);

    SubmissionLeaderboardDetailsDto getSubmissionDetails(UUID submissionId);
}
