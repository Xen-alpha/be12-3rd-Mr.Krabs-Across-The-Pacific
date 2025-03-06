package com.example.atp_back.portfolio.model.response;

import com.example.atp_back.portfolio.model.entity.Badge;
import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.user.model.User;
import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "포트폴리오 인스턴스를 리스트로 만드는 DTO")
public class PortfolioListResp {
  @Schema(description = "리스트로 만들어질 포트폴리오의 목록")
  private List<PortfolioInstanceResp> portfolioList = new ArrayList<>();

  public static PortfolioListResp from(@Nullable User user, List<PortfolioInstanceResp> portfolios) {
    List<PortfolioInstanceResp> instanceResp = portfolios.stream()
            .map(portfolio -> PortfolioInstanceResp.fromMain2(user, portfolio))
            .collect(Collectors.toList());
    return PortfolioListResp.builder()
            .portfolioList(instanceResp)
            .build();
  }
}