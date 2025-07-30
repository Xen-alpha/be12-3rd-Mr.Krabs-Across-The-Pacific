package com.example.atp_back.user.model.response;

import com.example.atp_back.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResp {
    private Long idx;
    private String image;

    public static LoginResp of(User user) {
        return LoginResp.builder()
                .idx(user.getIdx())
                .image(user.getProfileImage())
                .build();
    }
}
