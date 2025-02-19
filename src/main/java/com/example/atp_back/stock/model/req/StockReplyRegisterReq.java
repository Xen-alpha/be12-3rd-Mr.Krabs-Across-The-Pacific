package com.example.atp_back.stock.model.req;

import com.example.atp_back.stock.model.Stock;
import com.example.atp_back.stock.model.StockReply;
import com.example.atp_back.user.model.User;
import lombok.Getter;

@Getter
public class StockReplyRegisterReq {
    private String contents;

    public StockReply toEntity(User user, Stock stock) {
        StockReply stockReply = StockReply.builder()
                .user(user)
                .stock(stock)
                .build();
        stockReply.setContents(contents);
        return stockReply;
    }
}
