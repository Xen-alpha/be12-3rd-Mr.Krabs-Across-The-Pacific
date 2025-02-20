package com.example.atp_back.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserUpdateReq {
    private String name;
    private String email;
    private String password;
    private String image;
}
