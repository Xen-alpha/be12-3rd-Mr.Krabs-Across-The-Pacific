package com.example.atp_back.user;

import com.example.atp_back.user.model.User;
import com.example.atp_back.user.model.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {
    public List<UserFollow> findAllByFolloweeOrderByDate(User followee);
    public List<UserFollow> findAllByFollowerOrderByDate(User follower);
}
