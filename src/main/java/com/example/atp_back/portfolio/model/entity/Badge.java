package com.example.atp_back.portfolio.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String name;
    private String description;

    @OneToMany(mappedBy = "badge")
    private List<Reward> Rewards;
}

//INSERT INTO badge (name, description) VALUES ( 'badge1', '조회수가 1000회 이상인 포트폴리오'), ('bagde2', '북마크 수가 100개 이상인 포트폴리오'), ('bagde3', '생성된 지 1년 이상 된 포트폴리오 중 수익률 상위 10위');
