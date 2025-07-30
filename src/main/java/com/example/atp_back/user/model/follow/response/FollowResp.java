package com.example.atp_back.user.model.follow.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Schema(description = "팔로우 응답 내부 양식")
public class FollowResp {
    @Schema(description="팔로우한 사람의 이름", example="gildongHong")
    private String name;
    @Schema(description="팔로우한 사람의 이메일", example="example@example.com")
    private String image;
    @Schema(description = "팔로우한 사람의 등급", example="Bronze")
    private String tier;
}
