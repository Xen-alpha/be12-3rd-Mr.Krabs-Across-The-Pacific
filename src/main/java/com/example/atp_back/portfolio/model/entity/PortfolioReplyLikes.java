package com.example.atp_back.portfolio.model.entity;

import com.example.atp_back.stock.model.StockReply;
import com.example.atp_back.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "reply_id"})
)
public class PortfolioReplyLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name="reply_id", nullable = false)
    private PortfolioReply reply;
}
