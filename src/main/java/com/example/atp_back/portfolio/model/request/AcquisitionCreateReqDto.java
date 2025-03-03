package com.example.atp_back.portfolio.model.request;

import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.portfolio.model.entity.Acquisition;
import com.example.atp_back.stock.model.Stock;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class AcquisitionCreateReqDto {
    private Long stockIdx;
    private String stockName;
    private Long price;
    private BigDecimal quantity;

    public Acquisition toEntity(Stock stock, Portfolio portfolio) {
        return Acquisition.builder()
                .stock(stock)
                .price(price)
                .quantity(quantity)
                .portfolio(portfolio)
                .build();
    }
}
