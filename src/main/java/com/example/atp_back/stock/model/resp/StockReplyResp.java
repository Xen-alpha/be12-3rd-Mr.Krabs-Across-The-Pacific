package com.example.atp_back.stock.model.resp;


import com.example.atp_back.stock.model.StockReply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StockReplyResp {
    private Long idx;
    private Long stockId;
    private Long userId;
    private String userName;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer likesCount;

    public static StockReplyResp from (StockReply stockReply) {
        return StockReplyResp.builder()
                .idx(stockReply.getIdx())
                .stockId(stockReply.getStock().getIdx())
                .userId(stockReply.getUser().getIdx())
                .userName(stockReply.getUser().getName())
                .contents(stockReply.getContents())
                .createdAt(stockReply.getCreatedAt())
                .updatedAt(stockReply.getUpdatedAt())
                .likesCount(stockReply.getLikes().size())
                .build();
    }
}