package com.example.atp_back.portfolio.repository;

import com.example.atp_back.portfolio.model.entity.Bookmark;
import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByUserAndPortfolio(User user, Portfolio portfolio);
    int countByPortfolioIdx(Long portfolioIdx);
}
