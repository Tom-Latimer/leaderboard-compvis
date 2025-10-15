package com.sweets.leaderboard_compvis.challenges.services;

import com.sweets.leaderboard_compvis.challenges.models.DTO.ChallengeDto;
import com.sweets.leaderboard_compvis.challenges.models.DTO.CreateChallengeDto;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChallengeService {

    List<ChallengeDto> getChallengesPaged(Pageable pageable);

    @Transactional
    void createChallenge(CreateChallengeDto dto);

    ChallengeDto getChallengeById(Long id);
}