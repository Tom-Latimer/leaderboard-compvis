package com.sweets.leaderboard_compvis.challenges.controllers;

import com.sweets.leaderboard_compvis.challenges.models.DTO.*;
import com.sweets.leaderboard_compvis.challenges.services.ChallengeService;
import com.sweets.leaderboard_compvis.infrastructure.models.DTO.MessageResponse;
import com.sweets.leaderboard_compvis.infrastructure.models.DTO.PagedResponse;
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
import java.util.UUID;

@RestController
@RequestMapping("/api/challenges")
public class ChallengeController {

    private final ChallengeService challengeService;

    @Autowired
    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @GetMapping("")
    public ResponseEntity<PagedResponse<ChallengeDto>> getChallenges(
            @RequestParam int p,
            @RequestParam int s
    ) {
        Pageable pageable = PageRequest.of(p, s);

        PagedResponse<ChallengeDto> challenges = challengeService.getChallengesPaged(pageable);

        return ResponseEntity.ok(challenges);
    }

    @PostMapping("")
    public ResponseEntity<MessageResponse> createChallenge(@RequestBody CreateChallengeDto dto) {

        challengeService.createChallenge(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Challenge uploaded successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeDto> getChallengeById(@PathVariable long id) {
        return ResponseEntity.ok(challengeService.getChallengeById(id));
    }

    //can probably be removed with better implementation on frontend
    @GetMapping("/{id}/overview")
    public ResponseEntity<ChallengeOverviewDto> getChallengeOverview(@PathVariable long id) {
        return ResponseEntity.ok(challengeService.getChallengeOverview(id));
    }

    @PostMapping("/{id}/datasets/upload")
    public ResponseEntity<MessageResponse> uploadChallengeDataset(
            @PathVariable long id,
            @RequestParam("file") MultipartFile file) throws IOException {

        challengeService.uploadDataset(id, file);

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("File uploaded successfully"));
    }

    @GetMapping("/{id}/datasets/{attachmentId}/download")
    public ResponseEntity<InputStreamResource> downloadChallengeDataset(
            @PathVariable long id,
            @PathVariable UUID attachmentId) {

        FileDownloadDto fileDownloadDto = challengeService.downloadDataset(attachmentId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileDownloadDto.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(fileDownloadDto.getContentType().getValue()))
                .contentLength(fileDownloadDto.getContentLength())
                .body(new InputStreamResource(fileDownloadDto.getInputStream()));
    }

    @GetMapping("/{id}/datasets")
    public ResponseEntity<PagedResponse<DatasetDownloadDto>> getDatasets(
            @PathVariable long id,
            @RequestParam int p,
            @RequestParam int s
    ) {
        Pageable pageable = PageRequest.of(p, s);

        PagedResponse<DatasetDownloadDto> datasets = challengeService.getDatasetsByChallengeIdPaged(id, pageable);

        return ResponseEntity.ok(datasets);
    }
}
