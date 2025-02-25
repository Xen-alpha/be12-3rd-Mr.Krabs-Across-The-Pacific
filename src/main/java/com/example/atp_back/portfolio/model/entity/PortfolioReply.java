package com.example.atp_back.portfolio.model.entity;

import com.example.atp_back.common.BaseReply;
import com.example.atp_back.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioReply extends BaseReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name="portfolio_id", nullable=false)
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @OneToMany(mappedBy = "reply")
    private List<PortfolioReplyLikes> likes;
}
