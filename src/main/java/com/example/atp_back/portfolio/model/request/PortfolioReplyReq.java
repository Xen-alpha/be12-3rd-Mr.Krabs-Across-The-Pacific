package com.example.atp_back.portfolio.model.request;

import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.portfolio.model.entity.PortfolioReply;
import com.example.atp_back.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "포트폴리오 상세 페이지에서 해당 포트폴리오에 달린 댓글을 확인하는 DTO")
public class PortfolioReplyReq {
    @Schema(description = "포트폴리오에 작성할 댓글", example = "Very Nice!")
    @NotBlank
    private String contents;

    public PortfolioReply toEntity(User user, Portfolio portfolio) {
        PortfolioReply portfolioReply = PortfolioReply.builder()
                .user(user)
                .portfolio(portfolio)
                .build();
        portfolioReply.setContents(contents);
        return portfolioReply;
    }
}
