package com.example.atp_back.portfolio.model.request;

import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.user.model.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PortfolioCreateReqDto {
    private String name;
    private List<AcquisitionCreateReqDto> acquisitionList = new ArrayList<>();
    public Portfolio toEntity(User user) {
        return Portfolio.builder()
                .name(name)
                .user(user)
                .build();
    }
}
