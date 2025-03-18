package com.example.atp_back.user;

import com.example.atp_back.user.model.User;
import com.example.atp_back.user.model.follow.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {
    // TODO: N+1 문제 해결할 것 -> SELECT ... FROM user JOIN user_follow WHERE user_follow.follower = ... AND user_follow.followee
    @Query("select f from UserFollow f " +
            "join fetch f.followee f1 " +
            "join fetch f.follower f2 " +
            "where f.followee = :followee")
    public List<UserFollow> findAllByFolloweeOrderByDate(User followee);
    @Query("select f from UserFollow f " +
            "join fetch f.followee f1 " +
            "join fetch f.follower f2 " +
            "where f.follower = :follower")
    public List<UserFollow> findAllByFollowerOrderByDate(User follower);
    @Query("select f from UserFollow f " +
            "join fetch f.followee f1 " +
            "join fetch f.follower f2 " +
            "where f.followee = :followee and f.follower = :follower")
    public Optional<UserFollow> findByFolloweeAndFollower(User followee, User follower);
}
