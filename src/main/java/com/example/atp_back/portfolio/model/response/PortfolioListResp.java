package com.example.atp_back.portfolio.model.response;

import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.user.model.User;
import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioListResp {
  
  private List<PortfolioInstanceResp> portfolioList = new ArrayList<>();

  public static PortfolioListResp from(@Nullable User user, List<Portfolio> portfolios) {
    List<PortfolioInstanceResp> instanceResp = portfolios.stream()
            .map(portfolio -> PortfolioInstanceResp.from(user, portfolio))
            .collect(Collectors.toList());

    return PortfolioListResp.builder()
            .portfolioList(instanceResp)
            .build();
  }
}