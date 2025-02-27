package com.example.atp_back.portfolio.repository;

import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.portfolio.model.response.PortfolioInstanceResp;
import com.example.atp_back.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PortfolioCustomRepository {
  List<Portfolio> findAllByNameContaining(String name);
  List<Portfolio> findAllByUserNameContaining(String name);
  List<Portfolio> findAllByStockNameContaining(String name);

  Page<Portfolio> findAllByOrderByBookmarksDesc(Pageable pageable);

  void incrementViewCnt(Long portfolioIdx);

  List<PortfolioInstanceResp> findPortfolioWithBadges(User user);
}
