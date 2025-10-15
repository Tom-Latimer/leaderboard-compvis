package com.sweets.leaderboard_compvis.challenges.models.mapper;

import com.sweets.leaderboard_compvis.challenges.models.DTO.ChallengeDto;
import com.sweets.leaderboard_compvis.challenges.models.DTO.CreateChallengeDto;
import com.sweets.leaderboard_compvis.challenges.models.JPA.Challenge;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ChallengeMapper {
    CreateChallengeDto toCreateChallengeDto(Challenge challenge);

    ChallengeDto toChallengeDto(Challenge challenge);

    Challenge toEntity(CreateChallengeDto challengeDto);

    List<ChallengeDto> toCreateChallengeDto(List<Challenge> challenges);

    List<Challenge> toEntity(List<ChallengeDto> challengesDto);
}
