package com.sweets.leaderboard_compvis.challenges.models.mapper;

import com.sweets.leaderboard_compvis.challenges.models.JPA.SubmissionMetadata;
import com.sweets.leaderboard_compvis.challenges.models.submissions.DTO.SubmissionDto;
import com.sweets.leaderboard_compvis.challenges.models.submissions.DTO.SubmissionLeaderboardDto;
import com.sweets.leaderboard_compvis.challenges.models.submissions.DTO.SubmissionListItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface SubmissionMapper {

    @Mapping(target = "submissionId", source = "attachmentId")
    @Mapping(target = "challengeId", source = "challenge.id")
    @Mapping(target = "challengeName", source = "challenge.title")
    SubmissionListItemDto toSubmissionListItemDto(SubmissionMetadata submissionMetadata);

    List<SubmissionListItemDto> toSubmissionListItemDtoList(List<SubmissionMetadata> submissionMetadata);

    @Mapping(target = "submissionId", source = "attachmentId")
    SubmissionLeaderboardDto toSubmissionLeaderboardDto(SubmissionMetadata submissionMetadata);

    List<SubmissionLeaderboardDto> toSubmissionLeaderboardDtoList(List<SubmissionMetadata> submissionMetadata);

    SubmissionDto toSubmissionDto(SubmissionMetadata submissionMetadata);
}
