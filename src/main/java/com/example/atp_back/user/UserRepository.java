package com.example.atp_back.user;

import com.example.atp_back.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // TODO: N+1 문제 해결할 것: SELECT ... FROM user JOIN  WHERE user.email = ...
    @Query("select u from user u join fetch u.tier t" +
            " join fetch u.portfolio p " +
            "join fetch u.follower f " +
            "join fetch u.followee g " +
            "where u.email = :email")
    Optional<User> findByEmail(String email);
}
