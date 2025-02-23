package com.example.atp_back.stock.model.req;

import com.example.atp_back.stock.model.Stock;
import com.example.atp_back.stock.model.StockReply;
import com.example.atp_back.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class StockReplyRegisterReq {
    @Schema(example = "댓글 내용!!!")
    @NotBlank
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
