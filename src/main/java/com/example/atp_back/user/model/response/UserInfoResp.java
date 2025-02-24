package com.example.atp_back.user.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Tag(name = "사용자 정보", description="닉네임부터 등급까지의 특정 사용자 정보(패스워드 없음)")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserInfoResp {
    @Schema(description="닉네임")
    private String name;
    @Schema(description="이메일")
    private String email;
    @Schema(description="제작한 포트폴리오 수")
    private Integer portfolio_count;
    @Schema(description="나를 팔로우하는 사람 수")
    private Integer followers_count;
    @Schema(description="내가 팔로우하는 사람 수")
    private Integer followings_count;
    @Schema(description="사용자 이미지")
    private String image;
    @Schema(description="사용자 등급")
    private String tier;
}
