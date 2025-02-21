package com.example.atp_back.user.model.follow;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserFollowReq {
    @Schema(description="팔로우 할 사람의 이메일")
    private String email;
}
