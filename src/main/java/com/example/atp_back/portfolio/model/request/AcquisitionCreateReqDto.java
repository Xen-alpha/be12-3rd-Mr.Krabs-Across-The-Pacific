package com.example.atp_back.portfolio.model.request;

import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.portfolio.model.entity.Acquisition;
import com.example.atp_back.stock.model.Stock;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Schema(description = "포트폴리오를 생성하기 위한 Acquisition 요청 DTO")
public class AcquisitionCreateReqDto {
  @Schema(description= "종목 코드", example = "AAPL")
  private String code;
  @Schema(description = "주식 이름", example = "Apple Inc. Common Stock")
  private String name;
  @Schema(description = "매입 시점", example = "2024-10-08")
  private String date;
  @Schema(description = "매입 가격", example = "75000")
  private Long price;
  @Schema(description = "매입 수량", example = "10")
  private BigDecimal quantity;
  
  // TODO: 이건 쓸모 없는 엔티티 변환 메서드로, 문제가 없으면 제거할 것
    public Acquisition toEntity(Stock stock, Portfolio portfolio) {
        return Acquisition.builder()
                .stock(stock)
                .price(price)
                .quantity(quantity)
                .portfolio(portfolio)
                .build();
    }
}
