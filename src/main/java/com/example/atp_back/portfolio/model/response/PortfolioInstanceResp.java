package com.example.atp_back.portfolio.model.response;

import com.example.atp_back.portfolio.model.entity.Badge;
import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.portfolio.model.entity.PortfolioReply;
import com.example.atp_back.portfolio.model.entity.Reward;
import com.example.atp_back.user.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class PortfolioInstanceResp {
    private Long idx;
    private Long userIdx;
    private String name;
    private String imageUrl;
    private int viewCnt;
    private boolean bookmark;
    private boolean isOwn;
    private int bookmarkCnt;
    private int badges;
    private String profileImage;
    private List<Long> bookmarkUsers = new ArrayList<>();
    private List<AcquisitionInstanceResp> acquisitionList = new ArrayList<>();

    public static PortfolioInstanceResp fromMain(@Nullable User user, PortfolioInstanceResp portfolioResp) {
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
            .isOwn(user!=null && user.getIdx().equals(portfolio.getUser().getIdx()))// 포트폴리오 소유 여부 확인인
                .acquisitionList(portfolio.getAcquisitionList().stream().map(AcquisitionInstanceResp::from).collect(Collectors.toList()))
                .build();
    }

    //포트폴리오 상세 페이지 응답
    public static PortfolioInstanceResp fromDetail(Portfolio portfolio) {
        return PortfolioInstanceResp.builder()
                .idx(portfolio.getIdx())
                .name(portfolio.getName())
                .userIdx(portfolio.getUser().getIdx())
                .profileImage(portfolio.getUser().getProfileImage())
                .acquisitionList(portfolio.getAcquisitionList().stream().map(AcquisitionInstanceResp::from).collect(Collectors.toList()))
                .build();
    }
}