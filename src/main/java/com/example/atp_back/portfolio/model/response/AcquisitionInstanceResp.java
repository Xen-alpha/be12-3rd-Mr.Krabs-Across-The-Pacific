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

    public static AcquisitionInstanceResp from(Acquisition acquisition) {
        return AcquisitionInstanceResp.builder()
                .idx(acquisition.getIdx())
                .price(acquisition.getPrice())
                .quantity(acquisition.getQuantity())
                .orderAt(acquisition.getOrderAt())
                .stockName(acquisition.getStock().getName())
                .build();
    }
}
