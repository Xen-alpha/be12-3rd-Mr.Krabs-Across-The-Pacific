package com.example.atp_back.portfolio.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockSummaryResp {
  private String stockName;
  private BigDecimal totalStockPrice;
  private double percentage;
}
