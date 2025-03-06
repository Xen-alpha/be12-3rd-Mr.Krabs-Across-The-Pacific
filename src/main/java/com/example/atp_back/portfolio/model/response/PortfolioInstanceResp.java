package com.example.atp_back.portfolio.model.response;

import com.example.atp_back.portfolio.model.entity.Badge;
import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.portfolio.model.entity.PortfolioReply;
import com.example.atp_back.portfolio.model.entity.Reward;
import com.example.atp_back.user.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "포트폴리오 상세 정보")
public class PortfolioInstanceResp {
  @Schema(description = "포트폴리오 ID", example = "1")
    private Long idx;
  @Schema(description = "포트폴리오를 생성한 유저의 Idx", example = "101")
    private int userIdx;
  @Schema(description = "포트폴리오 이름", example = "Crab's Portfolio")
    private String name;
  @Schema(description = "생성된 포트폴리오의 정보를 담은 이미지")
    private String imageUrl;
  @Schema(description = "포트폴리오가 조회된 횟수", example = "100")
    private int viewCnt;
  @Schema(description = "포트폴리오가 북마크되었는지 여부", example = "false")
    private boolean bookmark;
  @Schema(description = "포트폴리오가 북마크된 횟수", example = "30")
    private int bookmarkCnt;
  @Schema(description = "포트폴리오에 부여된 뱃지 정보", example = "101")
    private int badges;
  @Schema(description = "포트폴리오를 북마크한 유저 목록")
    private List<Long> bookmarkUsers = new ArrayList<>();
  @Schema(description = "포트폴리오에 등록된 구매 주식 리스트")
    private List<AcquisitionInstanceResp> acquisitionList = new ArrayList<>();

    //포트폴리오 메인 페이지 응답
    public static PortfolioInstanceResp fromMain(@Nullable User user, Portfolio portfolio) {
        return PortfolioInstanceResp.builder()
                .idx(portfolio.getIdx())
                .name(portfolio.getName())
                .imageUrl(portfolio.getImageUrl())
                .viewCnt(portfolio.getViewCnt())
                .badges(portfolio.getBadges())
                .bookmark(user != null && portfolio.getBookmarkList().stream()
                        .anyMatch(bookmark -> bookmark.getUser().getIdx().equals(user.getIdx())))
                .acquisitionList(portfolio.getAcquisitionList().stream().map(AcquisitionInstanceResp::fromMain).collect(Collectors.toList()))
                .build();
    }

    public static PortfolioInstanceResp fromMain2(@Nullable User user, PortfolioInstanceResp portfolioResp) {
        return PortfolioInstanceResp.builder()
                .idx(portfolioResp.getIdx())
                .name(portfolioResp.getName())
                .imageUrl(portfolioResp.getImageUrl())
                .viewCnt(portfolioResp.getViewCnt())
                .badges(portfolioResp.getBadges())
                .bookmark(user != null && portfolioResp.getBookmarkUsers().contains(user.getIdx())) //북마크 여부 확인
                .bookmarkCnt(portfolioResp.getBookmarkCnt()) // 북마크 개수
                .acquisitionList(portfolioResp.getAcquisitionList())
                .build();
    }

    //포트폴리오 수정 페이지 응답
    public static PortfolioInstanceResp fromUpdate(User user, Portfolio portfolio) {
        return PortfolioInstanceResp.builder()
                .idx(portfolio.getIdx())
                .name(portfolio.getName())
                .acquisitionList(portfolio.getAcquisitionList().stream().map(AcquisitionInstanceResp::from).collect(Collectors.toList()))
                .build();
    }

    //포트폴리오 상세 페이지 응답
    public static PortfolioInstanceResp fromDetail(Portfolio portfolio) {
        return PortfolioInstanceResp.builder()
                .idx(portfolio.getIdx())
                .name(portfolio.getName())
                .acquisitionList(portfolio.getAcquisitionList().stream().map(AcquisitionInstanceResp::from).collect(Collectors.toList()))
                .build();
    }
}