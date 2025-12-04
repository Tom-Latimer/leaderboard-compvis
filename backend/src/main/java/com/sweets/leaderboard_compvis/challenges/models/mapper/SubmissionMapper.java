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
    @Mapping(target = "status", source = "submissionStatus")
    @Mapping(target = "submittedAt", source = "auditMetadata.createdAt")
    @Mapping(target = "rank", ignore = true)
    SubmissionListItemDto toSubmissionListItemDto(SubmissionMetadata submissionMetadata);

    @Mapping(target = "submissionId", source = "attachmentId")
    @Mapping(target = "rank", ignore = true)
    SubmissionLeaderboardDto toSubmissionLeaderboardDto(SubmissionMetadata submissionMetadata);

    @Mapping(target = "submissionId", source = "attachmentId")
    @Mapping(target = "teamMembers", source = "submissionTeamMembers")
    @Mapping(target = "rank", ignore = true)
    SubmissionLeaderboardDetailsDto toSubmissionLeaderboardDetailsDto(SubmissionMetadata submissionMetadata);

    @Mapping(target = "submissionId", source = "attachmentId")
    @Mapping(target = "status", source = "submissionStatus")
    @Mapping(target = "teamMembers", source = "submissionTeamMembers")
    @Mapping(target = "fileSize", source = "contentLength")
    @Mapping(target = "challengeName", source = "challenge.title")
    SubmissionDetailsDto toSubmissionDetailsDto(SubmissionMetadata submissionMetadata);

    List<SubmissionLeaderboardDto> toSubmissionLeaderboardDtoList(List<SubmissionMetadata> submissionMetadata);

    @Mapping(target = "status", source = "submissionStatus")
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
    @Mapping(target = "maxPrecision", ignore = true)
    @Mapping(target = "maxRecall", ignore = true)
    @Mapping(target = "split", ignore = true)
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
