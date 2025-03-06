package com.example.atp_back.portfolio.model.response;

import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "포트폴리오 리스트를 페이지네이션해서 보여주는 응답 DTO")
public class PortfolioPageResp {
  @Schema(description = "페이지 번호", example = "0")
    private int page;
  @Schema(description = "페이지 내에서 가져올 데이터 양", example = "30")
    private int size;
  @Schema(description = "가져온 데이터 총 개수", example = "30")
    private long totalElements;
  @Schema(description = "전체 페이지", example = "1")
    private int totalPages;
  @Schema(description = "다음 페이지가 있는지 여부", example = "true")
    private boolean hasNext;
  @Schema(description = "이전 페이지 있는지 여부", example = "false")
    private boolean hasPrevious;
  @Schema(description = "가져온 포트폴리오 상세 정보")
    private List<PortfolioInstanceResp> portfolioList;

    public static PortfolioPageResp from(@Nullable User user, Page<PortfolioInstanceResp> portfolioPage) {
        return PortfolioPageResp.builder()
                .page(portfolioPage.getNumber())
                .size(portfolioPage.getSize())
                .totalElements(portfolioPage.getTotalElements())
                .totalPages(portfolioPage.getTotalPages())
                .hasNext(portfolioPage.hasNext())
                .hasPrevious(portfolioPage.hasPrevious())
                .portfolioList(portfolioPage.stream()
                        .map(portfolioResp -> PortfolioInstanceResp.fromMain(user, portfolioResp))
                        .collect(Collectors.toList()))
                .build();
    }
}
