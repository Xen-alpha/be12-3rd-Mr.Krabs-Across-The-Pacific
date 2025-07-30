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
    // 메인 페이지에서 포트폴리오가 소유한 뱃지 정보를 표시하기 용이하도록 추가한 2진수 코드 (10, 100, 1000, ...)
    private int code;

    @OneToMany(mappedBy = "badge")
    private List<Reward> Rewards;
}

/*
INSERT INTO badge (name, description, code) VALUES
('badge1', '조회수가 1000회 이상인 포트폴리오', 2),
('badge2', '북마크 수가 100개 이상인 포트폴리오', 4),
('badge3', '생성된 지 1년 이상 된 포트폴리오 중 수익률 상위 10위', 8);
 */
