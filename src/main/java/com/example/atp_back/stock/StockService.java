package com.example.atp_back.stock;

import com.example.atp_back.stock.model.resp.StockDetailResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;

    public StockDetailResp getStock(Long idx) {
        return StockDetailResp.from(stockRepository.findById(idx).orElseThrow());
    }
}
