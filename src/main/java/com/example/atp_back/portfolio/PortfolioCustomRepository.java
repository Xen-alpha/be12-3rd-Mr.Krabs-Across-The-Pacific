package com.example.atp_back.portfolio;

import com.example.atp_back.portfolio.model.entity.Portfolio;

import java.util.List;

public interface PortfolioCustomRepository {
  List<Portfolio> findAllByNameContaining(String name);

  List<Portfolio> findAllByUserNameContaining(String name);

  List<Portfolio> findAllByStockNameContaining(String name);
}
