package com.example.atp_back.portfolio.repository;

import com.example.atp_back.portfolio.model.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
}
