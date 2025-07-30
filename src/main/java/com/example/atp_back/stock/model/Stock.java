package com.example.atp_back.stock.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, length = 10)
    private String code;
    @Column(nullable = false, length = 10)
    private String market;

    @OneToMany(mappedBy = "stock")
    private List<StockReply> replies;
}
