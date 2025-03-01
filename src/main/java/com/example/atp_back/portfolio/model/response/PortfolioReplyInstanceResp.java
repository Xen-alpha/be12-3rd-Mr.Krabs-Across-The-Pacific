package com.example.atp_back.portfolio.model.response;

import com.example.atp_back.portfolio.model.entity.PortfolioReply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PortfolioReplyInstanceResp {
    private Long idx;
    private Long userId;
    private String userName;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer likesCount;

    public static PortfolioReplyInstanceResp from(PortfolioReply reply) {
        return PortfolioReplyInstanceResp.builder()
                .idx(reply.getIdx())
                .userId(reply.getUser().getIdx())
                .userName(reply.getUser().getUsername())
                .contents(reply.getContents())
                .createdAt(reply.getCreatedAt())
                .updatedAt(reply.getUpdatedAt())
                .likesCount(reply.getLikes().size())
                .build();
    }

    public static List<PortfolioReplyInstanceResp> from(List<PortfolioReply> replyList) {
        return replyList.stream()
                .map(PortfolioReplyInstanceResp::from)
                .collect(Collectors.toList());
    }
}
