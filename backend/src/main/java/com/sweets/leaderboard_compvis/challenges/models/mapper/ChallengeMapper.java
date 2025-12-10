package com.sweets.leaderboard_compvis.challenges.models.mapper;

import com.sweets.leaderboard_compvis.challenges.models.DTO.ChallengeDto;
import com.sweets.leaderboard_compvis.challenges.models.DTO.ChallengeOverviewDto;
import com.sweets.leaderboard_compvis.challenges.models.DTO.CreateChallengeDto;
import com.sweets.leaderboard_compvis.challenges.models.DTO.DatasetDownloadDto;
import com.sweets.leaderboard_compvis.challenges.models.JPA.Challenge;
import com.sweets.leaderboard_compvis.challenges.models.JPA.DatasetMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ChallengeMapper {
    CreateChallengeDto toCreateChallengeDto(Challenge challenge);

    ChallengeDto toChallengeDto(Challenge challenge);

    Challenge toEntity(CreateChallengeDto challengeDto);

    ChallengeOverviewDto toChallengeOverviewDto(Challenge challenge);

    List<ChallengeDto> toCreateChallengeDto(List<Challenge> challenges);

    List<Challenge> toEntity(List<ChallengeDto> challengesDto);

    @Mapping(target = "fileSize", source = "contentLength")
    DatasetDownloadDto toDatasetDownloadDto(DatasetMetadata datasetMetadata);

    List<DatasetDownloadDto> toDatasetDownloadDtoList(List<DatasetMetadata> datasetMetadata);
}
