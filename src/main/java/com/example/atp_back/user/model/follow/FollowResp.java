package com.example.atp_back.user.model.follow;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class FollowResp {
    @Schema(description="팔로우한 사람의 닉네임")
    private String name;
    @Schema(description="팔로우한 사람의 이메일")
    private String image;
    @Schema(description = "팔로우한 사람의 등급")
    private String tier;
}
