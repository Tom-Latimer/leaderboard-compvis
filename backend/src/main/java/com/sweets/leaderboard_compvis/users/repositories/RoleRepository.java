package com.sweets.leaderboard_compvis.users.repositories;

import com.sweets.leaderboard_compvis.users.models.EUserRole;
import com.sweets.leaderboard_compvis.users.models.JPA.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByName(EUserRole name);

    List<UserRole> findByNameIn(Set<EUserRole> names);
}
