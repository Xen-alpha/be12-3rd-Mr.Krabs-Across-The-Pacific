package com.example.atp_back.portfolio.model.response;

import com.example.atp_back.portfolio.model.entity.Portfolio;
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
public class PortfolioInstanceRespDto{
    private Long idx;
    private String name;
    private String imageUrl;
    private List<AcquisitionInstanceRespDto> acquisitionList = new ArrayList<>();
    public static PortfolioInstanceRespDto from(Portfolio portfolio) {
        return PortfolioInstanceRespDto.builder()
                .idx(portfolio.getIdx())
                .name(portfolio.getName())
                .imageUrl(portfolio.getImageUrl())
                .acquisitionList(portfolio.getAcquisitionList().stream().map(AcquisitionInstanceRespDto::from).collect(Collectors.toList()))
                .build();
    }
}