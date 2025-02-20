package com.example.atp_back.stock.model;


import com.example.atp_back.user.model.User;
import jakarta.persistence.*;

@Entity
public class StockReplyLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
