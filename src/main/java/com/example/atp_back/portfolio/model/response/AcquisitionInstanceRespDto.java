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
public class AcquisitionInstanceRespDto {
    private Long idx;
    private Long price;
    private BigDecimal quantity;
    private LocalDateTime orderAt;
    private String stockName;

    public static AcquisitionInstanceRespDto from(Acquisition acquisition) {
        return AcquisitionInstanceRespDto.builder()
                .idx(acquisition.getIdx())
                .price(acquisition.getPrice())
                .quantity(acquisition.getQuantity())
                .stockName(acquisition.getStock().getName())
                .build();
    }
}
