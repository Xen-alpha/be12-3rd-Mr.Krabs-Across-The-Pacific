package com.example.atp_back.user.model.follow;

import com.example.atp_back.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class UserFollow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long idx;
    @Schema(description = "팔로우 요청 시각")
    public LocalDateTime date;

    @Schema(description = "팔로우 신청한 사람")
    @ManyToOne
    @JoinColumn(name="follower")
    public User follower;

    @Schema(description = "팔로우 대상")
    @ManyToOne
    @JoinColumn(name="followee")
    public User followee;

}
