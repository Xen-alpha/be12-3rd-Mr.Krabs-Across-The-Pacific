package com.example.atp_back.user.model.follow.response;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;

import java.util.List;

@Tag(name = "팔로우 중인 사람 목록", description="자신이 팔로우하는 사람 목록")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FolloweeResp {
    @Schema(description="내가 팔로우 하는 중인 사람 수")
    private int following;
    @Schema(description="내가 팔로우 하는 중인 사람들 정보")
    private List<FollowResp> users;
}
