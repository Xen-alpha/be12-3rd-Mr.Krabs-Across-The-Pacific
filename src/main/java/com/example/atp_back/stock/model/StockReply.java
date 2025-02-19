package com.example.atp_back.stock.model;

import com.example.atp_back.common.BaseReply;
import com.example.atp_back.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockReply extends BaseReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name="stock_id")
    private Stock stock;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
