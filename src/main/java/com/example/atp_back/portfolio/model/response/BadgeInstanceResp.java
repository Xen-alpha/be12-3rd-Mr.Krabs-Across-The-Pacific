package com.example.atp_back.portfolio.model.response;
import com.example.atp_back.portfolio.model.entity.Badge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BadgeInstanceResp {
    private Long idx;

    public static BadgeInstanceResp from(Badge badge) {
        return BadgeInstanceResp.builder()
                .idx(badge.getIdx())
                .build();
    }
}
