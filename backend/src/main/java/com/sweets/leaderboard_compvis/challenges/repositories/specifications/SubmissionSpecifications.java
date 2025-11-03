package com.sweets.leaderboard_compvis.challenges.repositories.specifications;

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
            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("submissionStatus"), filter.getStatus()));
            }
            if (filter.getSubmitterEmail() != null && !filter.getSubmitterEmail().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("submitterEmail")),
                        "%" + filter.getSubmitterEmail().toLowerCase() + "%"));
            }
            if (filter.getSubmitterName() != null && !filter.getSubmitterName().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("submitterName")),
                        "%" + filter.getSubmitterName().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
