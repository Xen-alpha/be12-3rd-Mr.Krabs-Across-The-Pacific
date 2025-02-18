package com.example.atp_back.stock.model;

import jakarta.persistence.*;
import lombok.*;

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
    private String name;
    @Column(unique = true, nullable = false, length = 10)
    private String code;
    @Column(unique = true, nullable = false, length = 10)
    private String market;
}
