package com.example.atp_back.portfolio.model.entity;


import com.example.atp_back.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String name;
    private Boolean isPublic;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String imageUrl; //메인 페이지에서 포트폴리오 이미지를 띄우기 위해 추가(현재 ERD에는 없음)

    @ManyToOne
    @JoinColumn(name="user_idx")
    private User user;

    @OneToMany(mappedBy = "portfolio")
    private List<Acquisition> acquisitionList;
}
