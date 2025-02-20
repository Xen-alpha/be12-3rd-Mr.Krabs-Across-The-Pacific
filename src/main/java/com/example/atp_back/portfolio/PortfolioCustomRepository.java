package com.example.atp_back.portfolio;

import com.example.atp_back.portfolio.model.entity.Portfolio;

public interface PortfolioCustomRepository {  Portfolio findAllByNameContaining(String name);
}
