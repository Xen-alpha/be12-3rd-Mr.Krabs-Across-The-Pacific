package com.example.atp_back.user.model.follow;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;

import java.util.List;

@Tag(name = "팔로우 중인 사람 목록", description="자신이 팔로우하는 사람 목록")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FolloweeResp {
    private int following;
    private List<FollowResp> users;
}
