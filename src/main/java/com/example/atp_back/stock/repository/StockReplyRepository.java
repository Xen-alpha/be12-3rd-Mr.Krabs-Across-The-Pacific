package com.example.atp_back.stock.repository;

import com.example.atp_back.stock.model.StockReply;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockReplyRepository extends JpaRepository<StockReply, Long> {
    @Query("SELECT r FROM StockReply r JOIN FETCH r.user JOIN FETCH r.stock ORDER BY r.createdAt DESC")
    Slice<StockReply> findAllByStockIdxOrderByCreatedAtDesc(Long stockIdx, Pageable pageable);
}
