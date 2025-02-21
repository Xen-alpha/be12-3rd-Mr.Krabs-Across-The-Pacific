package com.example.atp_back.user.model.follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class FollowResp {
    private String name;
    private String image;
    private String tier;
}
