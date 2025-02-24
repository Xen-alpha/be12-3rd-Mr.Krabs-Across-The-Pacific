package com.example.atp_back.portfolio.model.response;

import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.user.model.User;
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
    public static PortfolioInstanceResp from(@Nullable User user, Portfolio portfolio) {
        boolean isBookmarked =false;
        if (user != null) {
            isBookmarked = portfolio.getBookmarkList().stream()
                    .anyMatch(bookmark -> bookmark.getUser().getIdx().equals(user.getIdx()));
        }

        return PortfolioInstanceResp.builder()
                .idx(portfolio.getIdx())
                .name(portfolio.getName())
                .imageUrl(portfolio.getImageUrl())
                .viewCnt(portfolio.getViewCnt())
                .bookmark(isBookmarked)
                .acquisitionList(portfolio.getAcquisitionList().stream().map(AcquisitionInstanceResp::from).collect(Collectors.toList()))
                .build();
    }
}