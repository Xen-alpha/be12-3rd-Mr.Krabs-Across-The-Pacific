package com.example.atp_back.stock.model.resp;


import com.example.atp_back.stock.model.StockReply;
import com.example.atp_back.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    private Boolean isLiked;

    public Boolean getIsLiked() {
        return this.isLiked;
    }

    public static StockReplyResp from (StockReply stockReply, long isLiked) {
        return StockReplyResp.builder()
                .idx(stockReply.getIdx())
                .stockId(stockReply.getStock().getIdx())
                .userId(stockReply.getUser().getIdx())
                .userName(stockReply.getUser().getName())
                .contents(stockReply.getContents())
                .createdAt(stockReply.getCreatedAt())
                .updatedAt(stockReply.getUpdatedAt())
                .likesCount(stockReply.getLikesCount())
                .isLiked(isLiked==1)
                .build();
    }

    public static Slice<StockReplyResp> from (Slice<Object[]> stockReplies) {
        return stockReplies.map(stockReply -> StockReplyResp.from((StockReply)stockReply[0], (long)stockReply[1]));
    }
}