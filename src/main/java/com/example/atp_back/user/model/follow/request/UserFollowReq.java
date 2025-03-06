package com.example.atp_back.user.model.follow.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Schema(description = "팔로우 요청")
public class UserFollowReq {
    @Schema(description="팔로우 할 사람의 이메일", required = true, example="example@example.com")
    private String email;
}
