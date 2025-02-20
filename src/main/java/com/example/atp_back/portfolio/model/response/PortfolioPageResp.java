package com.example.atp_back.portfolio.model.response;

import com.example.atp_back.portfolio.model.entity.Portfolio;
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

    public static PortfolioPageResp from(Page<Portfolio> portfolioPage) {
        return PortfolioPageResp.builder()
                .page(portfolioPage.getNumber())
                .size(portfolioPage.getSize())
                .totalElements(portfolioPage.getTotalElements())
                .totalPages(portfolioPage.getTotalPages())
                .hasNext(portfolioPage.hasNext())
                .hasPrevious(portfolioPage.hasPrevious())
                .portfolioList(portfolioPage.stream().map(PortfolioInstanceResp::from).collect(Collectors.toList()))
                .build();
    }
}
