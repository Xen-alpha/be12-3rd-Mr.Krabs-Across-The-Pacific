package com.example.atp_back.portfolio.model.response;

import com.example.atp_back.portfolio.model.entity.Acquisition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcquisitionInstanceResp {
    private Long idx;
    private Long price;
    private BigDecimal quantity;
    private LocalDateTime orderAt;
    private Long portfolioIdx;
    private Long stockIdx;
    private String stockName;
    private BigDecimal stockPrice;

    //수정 페이지
    public static AcquisitionInstanceResp from(Acquisition acquisition) {
        return AcquisitionInstanceResp.builder()
                .idx(acquisition.getIdx())
                .price(acquisition.getPrice())
                .quantity(acquisition.getQuantity())
                .orderAt(acquisition.getOrderAt())
                .stockName(acquisition.getStock().getName())
                .build();
    }

    //메인 페이지
    public static AcquisitionInstanceResp fromMain(Acquisition acquisition) {
        BigDecimal stockPrice = acquisition.getQuantity().multiply(BigDecimal.valueOf(acquisition.getPrice()));
        return AcquisitionInstanceResp.builder()
                .price(acquisition.getPrice())
                .stockPrice(stockPrice)
                .portfolioIdx(acquisition.getPortfolio().getIdx())
                .build();
    }

    //메인 페이지
    public static AcquisitionInstanceResp of(AcquisitionInstanceResp acquisition) {
        BigDecimal stockPrice = acquisition.getQuantity().multiply(BigDecimal.valueOf(acquisition.getPrice()));
        return AcquisitionInstanceResp.builder()
                .stockPrice(stockPrice)
                .portfolioIdx(acquisition.getPortfolioIdx())
                .stockName(acquisition.getStockName())
                .stockIdx(acquisition.getStockIdx())
                .build();
    }
}
