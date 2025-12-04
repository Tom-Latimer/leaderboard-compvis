package com.sweets.leaderboard_compvis.challenges.repositories.specifications;

import com.sweets.leaderboard_compvis.challenges.models.ESubmissionStatus;
import com.sweets.leaderboard_compvis.challenges.models.JPA.SubmissionMetadata;
import com.sweets.leaderboard_compvis.challenges.models.submissions.DTO.SubmissionFilterDto;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SubmissionSpecifications {
    public static Specification<SubmissionMetadata> fromFilter(SubmissionFilterDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getChallengeId() != null) {
                predicates.add(cb.equal(root.get("challenge").get("id"), filter.getChallengeId()));
            }
            if (filter.getChallengeName() != null && !filter.getChallengeName().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("challenge").get("name")),
                        "%" + filter.getChallengeName().toLowerCase() + "%"));
            }
            if (filter.getSubmissionId() != null) {
                predicates.add(cb.equal(root.get("attachmentId"), filter.getSubmissionId()));
            }
            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("submissionStatus"), filter.getStatus()));
            }
            if (filter.getOrganization() != null && !filter.getOrganization().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("organization")),
                        "%" + filter.getOrganization().toLowerCase() + "%"));
            }
            if (filter.getTeamName() != null && !filter.getTeamName().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("teamName")),
                        "%" + filter.getTeamName().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<SubmissionMetadata> statusEquals(ESubmissionStatus status) {
        return (root, query, cb) -> cb.equal(root.get("submissionStatus"), status);
    }

    public static Specification<SubmissionMetadata> challengeIdEquals(Long challengeId) {
        return (root, query, cb) -> cb.equal(root.get("challenge").get("id"), challengeId);
    }
}
