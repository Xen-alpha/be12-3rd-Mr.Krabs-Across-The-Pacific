package com.example.atp_back.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
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
    public LocalDateTime date;
    @ManyToOne
    @JoinColumn(name="follower")
    public User follower;

    @ManyToOne
    @JoinColumn(name="followee")
    public User followee;
}
