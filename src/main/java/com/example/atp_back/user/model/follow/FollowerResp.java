package com.example.atp_back.user.model.follow;

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
public class FollowerResp {
    private int follower;
    private List<FollowResp> users;
}
