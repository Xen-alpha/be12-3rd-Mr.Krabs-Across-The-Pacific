package com.example.atp_back.stock.model.resp;

import com.example.atp_back.stock.model.Stock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockDetailResp {
    private Long id;
    private String name;
    private String code;
    private String market;
    public static StockDetailResp from(Stock stock) {
        return StockDetailResp.builder()
                .id(stock.getIdx())
                .name(stock.getName())
                .code(stock.getCode())
                .market(stock.getMarket())
                .build();
    }

    public static List<StockDetailResp> from(List<Stock> stocks) {
        return stocks.stream().map(StockDetailResp::from).collect(Collectors.toList());
    }
}
