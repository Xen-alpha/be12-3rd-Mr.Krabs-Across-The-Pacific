package com.example.atp_back.portfolio.model.response;

import com.example.atp_back.portfolio.model.entity.Badge;
import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.portfolio.model.entity.PortfolioReply;
import com.example.atp_back.portfolio.model.entity.Reward;
import com.example.atp_back.user.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioInstanceResp {
    private Long idx;
    private String name;
    private String imageUrl;
    private int viewCnt;
    private boolean bookmark;
    private List<AcquisitionInstanceResp> acquisitionList = new ArrayList<>();
    private List<PortfolioReplyInstanceResp> replyList = new ArrayList<>();

    //포트폴리오 메인 페이지 응답
    public static PortfolioInstanceResp fromMain(@Nullable User user, Portfolio portfolio) {
        return PortfolioInstanceResp.builder()
                .idx(portfolio.getIdx())
                .name(portfolio.getName())
                .imageUrl(portfolio.getImageUrl())
                .viewCnt(portfolio.getViewCnt())
                .bookmark(user != null && portfolio.getBookmarkList().stream()
                        .anyMatch(bookmark -> bookmark.getUser().getIdx().equals(user.getIdx())))
                .acquisitionList(portfolio.getAcquisitionList().stream().map(AcquisitionInstanceResp::from).collect(Collectors.toList()))
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