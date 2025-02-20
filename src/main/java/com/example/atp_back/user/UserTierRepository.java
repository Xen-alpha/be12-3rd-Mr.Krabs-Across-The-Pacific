package com.example.atp_back.user;

import com.example.atp_back.user.model.UserTier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTierRepository extends JpaRepository<UserTier, Long> {
    public Optional<UserTier> findByGrade(String grade);
}
