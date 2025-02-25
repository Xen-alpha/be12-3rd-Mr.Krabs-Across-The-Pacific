package com.example.atp_back.stock.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection="stock")
public class StockGraphDocument {
    @Id
    private String ObjectId;
    private Integer id;
    private String name;
    private String code;
    private String market;
    private Double price;
    private Long date;
}
