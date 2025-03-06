package com.example.atp_back.stock.repository;

import com.example.atp_back.stock.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    public Optional<Stock> findByCode(String code);
}
