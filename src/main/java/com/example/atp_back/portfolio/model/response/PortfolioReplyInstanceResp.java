package com.example.atp_back.portfolio.model.response;

import com.example.atp_back.portfolio.model.entity.PortfolioReply;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "포트폴리오 댓글 응답 DTO")
public class PortfolioReplyInstanceResp {
  @Schema(description = "댓글 ID", example = "1")
    private Long idx;
  @Schema(description = "댓글 작성자 Idx", example = "101")
    private Long userId;
  @Schema(description = "댓글 작성자 이름", example = "John Doe")
    private String userName;
  @Schema(description = "댓글 내용", example = "Very Nice!")
    private String contents;
  @Schema(description = "댓글 생성 일시", example = "2024-03-05T12:34:56")
    private LocalDateTime createdAt;
  @Schema(description = "댓글 수정 일시", example = "2024-03-05T13:45:00")
    private LocalDateTime updatedAt;
  @Schema(description = "좋아요 개수", example = "15")
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
