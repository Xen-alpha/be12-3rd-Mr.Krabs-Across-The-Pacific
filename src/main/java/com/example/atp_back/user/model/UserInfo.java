package com.example.atp_back.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserInfo {
    private String name;
    private String email;
    private Integer portfolio_count;
    private String image;
    private String tier;
}
