package com.sweets.leaderboard_compvis.challenges.services;

import com.sweets.leaderboard_compvis.challenges.models.DTO.ChallengeDto;
import com.sweets.leaderboard_compvis.challenges.models.DTO.CreateChallengeDto;
import com.sweets.leaderboard_compvis.challenges.models.JPA.Challenge;
import com.sweets.leaderboard_compvis.challenges.models.mapper.ChallengeMapper;
import com.sweets.leaderboard_compvis.challenges.repositories.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeMapper mapper;

    @Autowired
    public ChallengeServiceImpl(ChallengeRepository challengeRepository,
                                ChallengeMapper challengeMapper) {
        this.challengeRepository = challengeRepository;
        this.mapper = challengeMapper;
    }

    @Override
    public List<ChallengeDto> getChallengesPaged(Pageable pageable) {
        Page<Challenge> page = challengeRepository.findAll(pageable);

        List<ChallengeDto> challenges = mapper.toDto(page.getContent());

        return challenges;
    }

    @Override
    public void createChallenge(CreateChallengeDto dto) {
        Challenge challenge = mapper.toEntity(dto);

        //challenge.setCreatedAt(OffsetDateTime.now());
        challenge.setActive(true);

        challengeRepository.save(challenge);
    }
}
