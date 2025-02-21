package com.example.atp_back.stock.model.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockListResp {
    private Long id;
    private String name;
    private String code;
    private String market;
}
