package com.example.atp_back.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserUpdateReq {
    private String name;
    private String email;
    private String password;
    private String image;
}
