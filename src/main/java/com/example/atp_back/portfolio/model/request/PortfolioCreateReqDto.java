package com.example.atp_back.portfolio.model.request;

import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.user.model.User;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PortfolioCreateReqDto {
    private String name;
    private Boolean isPublic;
    private LocalDateTime createdAt;
    private List<AcquisitionCreateReqDto> acquisitionList = new ArrayList<>();
    public Portfolio toEntity(User user) {
        return Portfolio.builder()
                .name(name)
                .isPublic(true)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
    }
}
