package com.example.atp_back.portfolio.model.response;

import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.user.model.User;
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
public class PortfolioDetailResp {
    private Long idx;
    private String name;
    private String imageUrl;
    private int viewCnt;
    private boolean bookmark;
    private List<AcquisitionInstanceResp> acquisitionList = new ArrayList<>();
    private List<PortfolioReplyInstanceResp> replyList = new ArrayList<>();

    public static PortfolioDetailResp from(@Nullable User user, Portfolio portfolio) {
        boolean isBookmarked =false;
        if (user != null) {
            isBookmarked = portfolio.getBookmarkList().stream()
                    .anyMatch(bookmark -> bookmark.getUser().getIdx().equals(user.getIdx()));
        }

        return PortfolioDetailResp.builder()
                .idx(portfolio.getIdx())
                .name(portfolio.getName())
                .imageUrl(portfolio.getImageUrl())
                .viewCnt(portfolio.getViewCnt())
                .bookmark(isBookmarked)
                .acquisitionList(portfolio.getAcquisitionList().stream().map(AcquisitionInstanceResp::from).collect(Collectors.toList()))
                .replyList(portfolio.getPortfolioReplyList().stream().map(PortfolioReplyInstanceResp::from).collect(Collectors.toList()))
                .build();
    }
}
