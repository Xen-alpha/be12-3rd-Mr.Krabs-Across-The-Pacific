package com.example.atp_back.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class UserTier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Schema(description="유저 티어 이름(문자열): 브론즈부터 실버, 골드, 플래티넘까지")
    @Column(nullable = false)
    private String grade;

    @OneToMany(mappedBy = "tierGrade")
    List<User> userList;
}