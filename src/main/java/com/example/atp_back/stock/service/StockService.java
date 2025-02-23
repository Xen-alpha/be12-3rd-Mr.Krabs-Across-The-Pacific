package com.example.atp_back.stock.service;

import com.example.atp_back.stock.model.resp.StockListResp;
import com.example.atp_back.stock.repository.StockRepository;
import com.example.atp_back.stock.model.resp.StockDetailResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;

    public StockDetailResp getStock(Long idx) {
        return StockDetailResp.from(stockRepository.findById(idx).orElseThrow());
    }

    public List<StockListResp> getAllStocks() {
        return StockListResp.from(stockRepository.findAll());
    }
}
