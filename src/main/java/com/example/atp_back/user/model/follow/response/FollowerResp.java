package com.example.atp_back.user.model.follow.response;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Tag(name = "팔로우하는 사람 목록", description="자신을 팔로우하는 다른 사람 목록")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "팔로우 하는 사람들 목록 응답 양식")
public class FollowerResp {
    @Schema(description="내가 팔로우 하는 중인 사람 수", example="2")
    private int follower;
    @Schema(description="나를 팔로우 하는 중인 사람들 정보")
    private List<FollowResp> users;
}
