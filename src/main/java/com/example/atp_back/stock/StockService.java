package com.example.atp_back.stock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {
    StockRepository stockRepository;
}
