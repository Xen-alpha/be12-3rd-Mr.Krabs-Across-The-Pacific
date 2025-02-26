package com.example.atp_back.portfolio;

import com.example.atp_back.portfolio.model.entity.Badge;
import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.portfolio.model.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardRepository extends JpaRepository<Reward, Long> {
    boolean existsByPortfolioAndBadge(Portfolio portfolio, Badge badge);
}
