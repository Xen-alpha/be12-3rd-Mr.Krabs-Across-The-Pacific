package com.example.atp_back.user.model.request;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Tag(name = "유저 정보 갱신", description="자신의 정보 갱신 요청용 DTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserUpdateReq {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private String image;
}
