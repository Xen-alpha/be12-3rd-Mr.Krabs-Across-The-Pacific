package com.example.atp_back.portfolio.model.entity;

import com.example.atp_back.stock.model.Stock;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Acquisition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private LocalDateTime orderAt;
    //price와 quantity의 자료형은 ERD에서는 INT로 지정되어있으나, 정확한 계산을 위해 각각에 알맞은 자료형으로 수정했음
    private Long price; // 주식 가격
    private BigDecimal quantity; // 구매 수량 (소수점 가능)

    @ManyToOne
    @JoinColumn(name = "portfolio_idx")
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "stock_idx")
    private Stock stock;
}