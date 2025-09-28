package com.sweets.leaderboard_compvis.challenges.controllers;

import com.sweets.leaderboard_compvis.challenges.models.DTO.ChallengeDto;
import com.sweets.leaderboard_compvis.challenges.models.DTO.CreateChallengeDto;
import com.sweets.leaderboard_compvis.challenges.services.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChallengeController {

    private final ChallengeService challengeService;

    @Autowired
    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @GetMapping("/challenges")
    public ResponseEntity<List<ChallengeDto>> getChallenges(
            @RequestParam int p,
            @RequestParam int s
    ) {
        Pageable pageable = PageRequest.of(p, s);

        List<ChallengeDto> challenges = challengeService.getChallengesPaged(pageable);

        return ResponseEntity.ok(challenges);
    }

    @PostMapping("/challenges")
    public ResponseEntity<String> createChallenge(@RequestBody CreateChallengeDto dto) {

        challengeService.createChallenge(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body("Test");
    }
}
