package com.example.atp_back.stock.repository;

import com.example.atp_back.stock.model.StockGraphDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockGraphRepository extends MongoRepository<StockGraphDocument, String> {
    public Iterable<StockGraphDocument> findAllByCodeOrderByDateAsc(String code);
}
