package com.sweets.leaderboard_compvis.teams.repositories;

import com.sweets.leaderboard_compvis.teams.models.JPA.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
