package com.example.atp_back.stock.repository;

import com.example.atp_back.stock.model.StockReply;
import com.example.atp_back.stock.model.resp.StockReplyResp;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockReplyRepository extends JpaRepository<StockReply, Long> {
    @Query("SELECT new com.example.atp_back.stock.model.resp.StockReplyResp(" +
            "r.idx, r.stock.idx, r.user.idx, r.user.name, r.contents, r.createdAt, r.updatedAt, r.likesCount, " +
            "CASE WHEN EXISTS (SELECT 1 FROM r.likes l WHERE l.user.idx = :userIdx) THEN true ELSE false END) " +
            "FROM StockReply r JOIN r.user JOIN r.stock ORDER BY r.createdAt DESC")
    Slice<StockReplyResp> findAllByStockIdxOrderByCreatedAtDesc(Long stockIdx, Long userIdx, Pageable pageable);
}
