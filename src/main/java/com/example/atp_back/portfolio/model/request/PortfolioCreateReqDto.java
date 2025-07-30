package com.example.atp_back.portfolio.model.request;

import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Schema(description = "포트폴리오 생성 요청 DTO")
public class PortfolioCreateReqDto {
  @Schema(description = "포트폴리오 이름", example = "Crab's Portfolio")
    private String name;
  @Schema(description = "타 유저에게 보여줄지 여부")
    private Boolean isPublic;
  @Schema(description = "포트폴리오에 등록되는 구매 주식 목록")
    private List<AcquisitionCreateReqDto> acquisitionList;
    public Portfolio toEntity(User user) {
        return Portfolio.builder()
                .name(name)
                .isPublic(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(user)
                .build();
    }
}
