package com.example.atp_back.portfolio.model.request;

import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.portfolio.model.entity.PortfolioReply;
import com.example.atp_back.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class PortfolioReplyReq {
    @Schema(example = "댓글 내용!!!")
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
