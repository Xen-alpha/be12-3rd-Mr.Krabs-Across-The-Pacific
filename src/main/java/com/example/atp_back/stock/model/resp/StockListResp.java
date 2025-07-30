package com.example.atp_back.stock.model.resp;

import com.example.atp_back.stock.model.Stock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockListResp {
    private Long id;
    private String name;
    private String code;
    private String market;

    public static StockListResp from(Stock stock) {
        return StockListResp.builder()
                .id(stock.getIdx())
                .name(stock.getName())
                .code(stock.getCode())
                .market(stock.getMarket())
                .build();
    }

    public static List<StockListResp> from(List<Stock> stocks) {
        return stocks.stream().map(StockListResp::from).collect(Collectors.toList());
    }
}
