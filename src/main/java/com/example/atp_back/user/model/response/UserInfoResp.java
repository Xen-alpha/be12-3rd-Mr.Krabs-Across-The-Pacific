package com.example.atp_back.user.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Tag(name = "사용자 정보", description="닉네임부터 등급까지의 특정 사용자 정보(패스워드 없음)")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Schema(description = "사용자 정보 반환 응답 양식")
public class UserInfoResp {
    @Schema(description="닉네임", example = "gildong")
    private String name;
    @Schema(description="이메일", example = "example@example.com")
    private String email;
    @Schema(description="제작한 포트폴리오 수", example = "11")
    private Integer portfolio_count;
    @Schema(description="나를 팔로우하는 사람 수", example = "2")
    private Integer followers_count;
    @Schema(description="내가 팔로우하는 사람 수", example = "4")
    private Integer followings_count;
    @Schema(description="사용자 이미지", example = "/sample/url/image")
    private String image;
    @Schema(description="사용자 등급", example = "Bronze")
    private String tier;
}
