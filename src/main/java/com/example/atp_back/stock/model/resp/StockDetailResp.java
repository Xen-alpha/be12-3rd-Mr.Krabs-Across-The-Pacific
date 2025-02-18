package com.example.atp_back.stock.model.resp;

import com.example.atp_back.stock.model.Stock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockDetailResp {
    private String name;
    private String code;
    private String market;
    public static StockDetailResp from(Stock stock) {
        return StockDetailResp.builder()
                .name(stock.getName())
                .code(stock.getCode())
                .market(stock.getMarket())
                .build();
    }
}
