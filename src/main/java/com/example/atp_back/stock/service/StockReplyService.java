package com.example.atp_back.stock.service;

import com.example.atp_back.common.BaseResponse;
import com.example.atp_back.stock.repository.StockReplyRepository;
import com.example.atp_back.stock.model.Stock;
import com.example.atp_back.stock.model.StockReply;
import com.example.atp_back.stock.model.req.StockReplyRegisterReq;
import com.example.atp_back.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockReplyService {
    private final StockReplyRepository stockReplyRepository;

    @Transactional
    public void addReply(StockReplyRegisterReq dto,User user, Long stockIdx) {
        Stock stock = new Stock();
        stock.setIdx(stockIdx);
        StockReply stockReply = dto.toEntity(user, stock);
        stockReplyRepository.save(stockReply);

    }
}
