package com.example.atp_back.portfolio.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "portfolio_idx")
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "badge_idx")
    private Badge badge;
}
