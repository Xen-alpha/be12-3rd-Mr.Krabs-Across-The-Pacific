package com.example.atp_back.stock.model.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StockGraphResp {
    private String name;
    private String code;
    private String market;
    private List<String> dates;
    private List<Double> prices;
}
