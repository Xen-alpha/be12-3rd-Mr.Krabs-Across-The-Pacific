package com.example.atp_back.stock;

import com.example.atp_back.common.BaseResponse;
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
    public BaseResponse<String> addReply(StockReplyRegisterReq dto, Long userIdx, Long stockIdx) {
        User user = new User();
        user.setIdx(userIdx);
        Stock stock = new Stock();
        stock.setIdx(stockIdx);
        StockReply stockReply = dto.toEntity(user, stock);
        stockReplyRepository.save(stockReply);

        BaseResponse<String> response = new BaseResponse<>();
        response.success("success");
        return response;
    }
}
