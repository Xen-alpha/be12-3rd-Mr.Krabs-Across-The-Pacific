package com.example.atp_back.portfolio.repository;

import com.example.atp_back.portfolio.model.entity.PortfolioReply;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioReplyRepository extends JpaRepository<PortfolioReply, Long> {
    Slice<PortfolioReply> findByPortfolioIdxOrderByCreatedAtDesc(Long portfolioIdx, Pageable pageable);
}
