package com.example.atp_back.portfolio.model.request;

import com.example.atp_back.portfolio.model.entity.Bookmark;
import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;


@Getter
@Schema(description = "포트폴리오 bookmark 요청")
public class PortfolioBookmarkReq {
  @Schema(description = "북마크하려는 포트폴리오의 ID", example = "1")
    private Long portfolioIdx;
  @Schema(description = "유저가 이미 북마크를 했는지 여부를 확인", example = "false")
    private boolean bookmark;
}