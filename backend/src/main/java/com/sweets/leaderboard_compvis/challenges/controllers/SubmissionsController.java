package com.sweets.leaderboard_compvis.challenges.controllers;

import com.sweets.leaderboard_compvis.challenges.models.DTO.FileDownloadDto;
import com.sweets.leaderboard_compvis.challenges.models.submissions.DTO.*;
import com.sweets.leaderboard_compvis.challenges.services.ChallengeService;
import com.sweets.leaderboard_compvis.challenges.services.SubmissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class SubmissionsController {

    private final ChallengeService challengeService;
    private final SubmissionService submissionService;

    @Autowired
    public SubmissionsController(ChallengeService challengeService, SubmissionService submissionService) {
        this.challengeService = challengeService;
        this.submissionService = submissionService;
    }

    @GetMapping("/challenges/{id}/submissions/{submissionId}/download")
    public ResponseEntity<InputStreamResource> downloadSubmission(
            @PathVariable long id,
            @PathVariable UUID submissionId) {

        FileDownloadDto fileDownloadDto = submissionService.downloadSubmission(submissionId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileDownloadDto.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(fileDownloadDto.getContentType().getValue()))
                .contentLength(fileDownloadDto.getContentLength())
                .body(new InputStreamResource(fileDownloadDto.getInputStream()));
    }

    @PostMapping("/challenges/{id}/submissions")
    public ResponseEntity<SubmissionDto> uploadSubmission(
            @RequestPart("file") MultipartFile file,
            @RequestPart("metadata") @Valid ChallengeSubmitUploadDto uploadDto,
            @PathVariable long id) throws IOException {

        SubmissionDto dto = submissionService.uploadChallengeSubmission(id, uploadDto, file);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/challenges/{id}/submissions")
    public ResponseEntity<List<SubmissionLeaderboardDto>> getSubmissionsByChallenge(
            @PathVariable long id,
            @RequestParam int p,
            @RequestParam int s
    ) {
        Pageable pageable = PageRequest.of(p, s);

        List<SubmissionLeaderboardDto> submissions = submissionService.getSubmissionsByChallengeIdPaged(id, pageable);

        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/submissions")
    public ResponseEntity<List<SubmissionListItemDto>> getSubmissions(SubmissionFilterDto filter, Pageable pageable) {

        List<SubmissionListItemDto> submissions = submissionService.getChallengeSubmissionsForModeration(filter,
                pageable);

        return ResponseEntity.ok(submissions);
    }

    @PostMapping("/submissions/{submissionId}/approve")
    public ResponseEntity<SubmissionStatusDto> approveSubmission(
            @PathVariable UUID submissionId) {

        SubmissionStatusDto response = submissionService.approveSubmission(submissionId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/submissions/{submissionId}/reject")
    public ResponseEntity<SubmissionStatusDto> rejectSubmission(
            @PathVariable UUID submissionId) {

        SubmissionStatusDto response = submissionService.rejectSubmission(submissionId);

        return ResponseEntity.ok(response);
    }
}
