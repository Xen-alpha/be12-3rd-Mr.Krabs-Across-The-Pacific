package com.example.atp_back.portfolio.model.response;

import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.user.model.User;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioPageResp {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
    private List<PortfolioInstanceResp> portfolioList;

    public static PortfolioPageResp from(@Nullable User user, Page<Portfolio> portfolioPage) {
        return PortfolioPageResp.builder()
                .page(portfolioPage.getNumber())
                .size(portfolioPage.getSize())
                .totalElements(portfolioPage.getTotalElements())
                .totalPages(portfolioPage.getTotalPages())
                .hasNext(portfolioPage.hasNext())
                .hasPrevious(portfolioPage.hasPrevious())
                .portfolioList(portfolioPage.stream()
                        .map(portfolio -> PortfolioInstanceResp.fromMain(user, portfolio))
                        .collect(Collectors.toList()))
                .build();
    }

    public static PortfolioPageResp from2(@Nullable User user, Page<PortfolioInstanceResp> portfolioPage) {
        return PortfolioPageResp.builder()
                .page(portfolioPage.getNumber())
                .size(portfolioPage.getSize())
                .totalElements(portfolioPage.getTotalElements())
                .totalPages(portfolioPage.getTotalPages())
                .hasNext(portfolioPage.hasNext())
                .hasPrevious(portfolioPage.hasPrevious())
                .portfolioList(portfolioPage.stream()
                        .map(portfolioResp -> PortfolioInstanceResp.fromMain2(user, portfolioResp))
                        .collect(Collectors.toList()))
                .build();
    }
}
