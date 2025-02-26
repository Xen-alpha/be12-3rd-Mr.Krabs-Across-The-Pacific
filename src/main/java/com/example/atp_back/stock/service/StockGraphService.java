package com.example.atp_back.stock.service;

import com.example.atp_back.stock.model.StockGraphDocument;
import com.example.atp_back.stock.model.resp.*;
import com.example.atp_back.stock.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StockGraphService {
    private final StockGraphRepository stockGraphRepository;

    @Transactional
    public List<StockGraphResp> getGraphList(List<String> tickerCodes) {
        List<StockGraphResp> dtos = new ArrayList<>();
        for (String tickerCode : tickerCodes) {
            List<StockGraphDocument> rows = (List<StockGraphDocument>) stockGraphRepository.findAllByCodeOrderByDateAsc(tickerCode);
            if (rows.size() > 0) {
                StockGraphResp stockGraphDto = new StockGraphResp();
                stockGraphDto.setMarket(rows.get(0).getMarket());
                stockGraphDto.setCode(rows.get(0).getCode());
                stockGraphDto.setName(rows.get(0).getName());
                List<Double> prices = new ArrayList<>();
                List<String> dates = new ArrayList<>();
                for (StockGraphDocument row : rows) {
                    Date date = new Date(row.getDate());
                    dates.add((1900 + date.getYear()) + "-" + (date.getMonth()+1) + "-" + date.getDate());
                    prices.add(row.getPrice());
                }
                stockGraphDto.setPrices(prices);
                stockGraphDto.setDates(dates);
                dtos.add(stockGraphDto);
            }
        }
        return dtos;
    }
    @Transactional
    public StockGraphResp getGraph(String tickerCode) {
        List<StockGraphDocument> rows = (List<StockGraphDocument>) stockGraphRepository.findAllByCodeOrderByDateAsc(tickerCode);
        StockGraphResp stockGraphDto = new StockGraphResp();
        if (rows.size() > 0) {
            stockGraphDto.setMarket(rows.get(0).getMarket());
            stockGraphDto.setCode(rows.get(0).getCode());
            stockGraphDto.setName(rows.get(0).getName());
            List<Double> prices = new ArrayList<>();
            List<String> dates = new ArrayList<>();
            for (StockGraphDocument row : rows) {
                Date date = new Date(row.getDate());
                dates.add((1900 + date.getYear()) + "-" + (date.getMonth()+1) + "-" + date.getDate());
                prices.add(row.getPrice());
            }
            stockGraphDto.setPrices(prices);
            stockGraphDto.setDates(dates);
        }
        return stockGraphDto;
    }
}
