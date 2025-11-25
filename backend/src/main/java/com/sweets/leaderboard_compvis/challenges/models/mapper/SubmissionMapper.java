package com.sweets.leaderboard_compvis.challenges.models.mapper;

import com.sweets.leaderboard_compvis.challenges.models.EMimeTypes;
import com.sweets.leaderboard_compvis.challenges.models.ESubmissionStatus;
import com.sweets.leaderboard_compvis.challenges.models.JPA.Challenge;
import com.sweets.leaderboard_compvis.challenges.models.JPA.SubmissionMetadata;
import com.sweets.leaderboard_compvis.challenges.models.JPA.SubmissionTeamMember;
import com.sweets.leaderboard_compvis.challenges.models.submissions.DTO.*;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.UUID;

@Mapper
public interface SubmissionMapper {

    @Mapping(target = "submissionId", source = "attachmentId")
    @Mapping(target = "challengeId", source = "challenge.id")
    @Mapping(target = "challengeName", source = "challenge.title")
    SubmissionListItemDto toSubmissionListItemDto(SubmissionMetadata submissionMetadata);

    List<SubmissionListItemDto> toSubmissionListItemDtoList(List<SubmissionMetadata> submissionMetadata);

    @Mapping(target = "submissionId", source = "attachmentId")
    SubmissionLeaderboardDto toSubmissionLeaderboardDto(SubmissionMetadata submissionMetadata);

    @Mapping(target = "submissionId", source = "attachmentId")
    @Mapping(target = "teamMembers", source = "submissionTeamMembers")
    SubmissionLeaderboardDetailsDto toSubmissionLeaderboardDetailsDto(SubmissionMetadata submissionMetadata);

    List<SubmissionLeaderboardDto> toSubmissionLeaderboardDtoList(List<SubmissionMetadata> submissionMetadata);

    SubmissionDto toSubmissionDto(SubmissionMetadata submissionMetadata);

    @Mapping(target = "id", ignore = true)
    SubmissionTeamMember toSubmissionTeamMember(SubmissionTeamMemberDto teamMemberDto);

    @Mapping(source = "uploadDto.teamName", target = "teamName")
    @Mapping(source = "uploadDto.organization", target = "organization")
    @Mapping(source = "uploadDto.teamMembers", target = "submissionTeamMembers")
    @Mapping(source = "challenge", target = "challenge")
    @Mapping(source = "filepath", target = "storageKey")
    @Mapping(source = "mimeType", target = "contentType")
    @Mapping(source = "fileSize", target = "contentLength")
    @Mapping(source = "status", target = "submissionStatus")
    @Mapping(target = "id", ignore = true)
    SubmissionMetadata toSubmissionMetadata(
            ChallengeSubmitUploadDto uploadDto,
            Challenge challenge,
            UUID attachmentId,
            String filepath,
            String fileName,
            EMimeTypes mimeType,
            long fileSize,
            ESubmissionStatus status
    );

    @AfterMapping
    default void linkTeamMembers(
            @MappingTarget SubmissionMetadata submission,
            ChallengeSubmitUploadDto dto
    ) {
        if (submission.getSubmissionTeamMembers() == null) {
            return;
        }

        submission.getSubmissionTeamMembers().forEach(teamMember ->
                teamMember.setSubmissionMetadata(submission));
    }
}
