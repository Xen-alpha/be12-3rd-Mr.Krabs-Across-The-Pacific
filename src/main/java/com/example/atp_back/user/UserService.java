package com.example.atp_back.user;

import com.example.atp_back.user.model.*;
import com.example.atp_back.user.model.follow.response.FollowResp;
import com.example.atp_back.user.model.follow.UserFollow;
import com.example.atp_back.user.model.follow.response.FolloweeResp;
import com.example.atp_back.user.model.follow.response.FollowerResp;
import com.example.atp_back.user.model.request.SignupReq;
import com.example.atp_back.user.model.response.UserInfoResp;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserTierRepository userTierRepository;
    private final UserFollowRepository userFollowRepository;
    private final EntityManager entityManager;

    @Value("${filepath.default}")
    private String defaultFilePath;
    /*
    public String uploadUserImage(MultipartFile file) {
        // TODO: defaultFilePath + "/images/avatar/" + randomPath + "_" + file.getOriginalFilename() 에 파일 저장
        String randomPath = UUID.randomUUID().toString();
        return "/images/avatar/" + randomPath + "_" + file.getOriginalFilename();
    }
    */
    @Transactional
    public void RegisterUser(SignupReq signupReq) {
        LocalDate thistime = LocalDate.now();
        UserTier initialTier = userTierRepository.findByGrade("Bronze").orElse(null);
        // TODO: File upload 위치 결정하고 업로드 함수 삽입
        /*
        String path = null;
        if (!file.isEmpty()){
            path = uploadUserImage(file);
        }
        */
        User user = User.builder().name(signupReq.getName()).email(signupReq.getEmail())
                .password(passwordEncoder.encode(signupReq.getPassword()))
                .profileImage(signupReq.getImage()) // TODO
                .createdAt(thistime)
                .updatedAt(thistime)
                .role("ROLE_USER")
                .tierGrade(initialTier)
                .followCount(0)
                .followingCount(0)
                .build();
        userRepository.save(user);
    }

    public UserInfoResp getUserInfo(@NotNull String email) {
        Optional<User> userResult = userRepository.findByEmail(email);
        if (userResult.isPresent()) {
            User userinfo = userResult.get();
            int portfolioCount = 0;
            if (userinfo.getPortfolios() != null) {
                portfolioCount = userinfo.getPortfolios().size();
            }
            return UserInfoResp.builder()
                    .email(userinfo.getEmail())
                    .name(userinfo.getName())
                    .tier(userinfo.getTierGrade().getGrade())
                    .image(userinfo.getProfileImage())
                    .portfolio_count(portfolioCount)
                    .followers_count(userinfo.getFollowCount())
                    .followings_count(userinfo.getFollowingCount())
                    .build();
        }
        return null;
    }
    @Operation(description="follower가 followee를 팔로우함")
    @Transactional
    public void follow(@NotNull String followeeMail, @NotNull String followerMail) {
        if (followeeMail.equals(followerMail)) {
            throw new RuntimeException("bad follow");
        }
        User follower = userRepository.findByEmail(followerMail).orElse(null);
        User followee = userRepository.findByEmail(followeeMail).orElse(null);
        UserFollow prev = userFollowRepository.findByFolloweeAndFollower(followee, follower).orElse(null);
        if (prev != null) {
            throw new RuntimeException("bad follow");
        }
        if (follower != null && followee != null) {
            follower.addFollowing();
            followee.addFollower();
            UserFollow follow = UserFollow.builder().follower(follower).followee(followee).date(LocalDateTime.now()).build();
            userFollowRepository.save(follow);
        } else {
            throw new RuntimeException("Failed to follow");
        }
    }

    @Operation(description="follower가 followee를 언팔로우함")
    @Transactional
    public void unfollow(@NotNull String followeeMail, @NotNull String followerMail) {
        if (followeeMail.equals(followerMail)) {
            throw new RuntimeException("bad unfollow");
        }
        User follower = userRepository.findByEmail(followerMail).orElse(null);
        User followee = userRepository.findByEmail(followeeMail).orElse(null);
        if (follower != null && followee != null) {
            UserFollow follow = userFollowRepository.findByFolloweeAndFollower(followee, follower).orElse(null);
            if (follow != null) {
                userFollowRepository.delete(follow);
                follower.removeFollowing();
                followee.removeFollower();
                User follower2 = userRepository.save(follower);
                User followee2 = userRepository.save(followee);
            }
        } else {
            throw new RuntimeException("Failed to unfollow");
        }
    }



    @Operation(description="follower 수 가져오기")
    public FollowerResp getFollowers(@NotNull String followeeEmail) {
        User followee = userRepository.findByEmail(followeeEmail).orElse(null);
        List<FollowResp> result = new ArrayList<>();
        if (followee != null) {
            List<UserFollow> follows = userFollowRepository.findAllByFolloweeOrderByDate(followee);
            for (UserFollow follow : follows) {
                User user = follow.getFollower();
                if (user != null) {
                    result.add(user.toFollowResp());
                }
            }
        }
        FollowerResp follower = new FollowerResp();
        follower.setFollower(result.size());
        follower.setUsers(result);
        return follower;
    }
    @Operation(description="following 수 가져오기")
    public FolloweeResp getFollowees(@NotNull String followerEmail) {
        User follower = userRepository.findByEmail(followerEmail).orElse(null);
        List<FollowResp> result = new ArrayList<>();
        if (follower != null) {
            List<UserFollow> follows = userFollowRepository.findAllByFollowerOrderByDate(follower);
            for (UserFollow follow : follows) {
                User user = follow.getFollowee();
                if (user != null) {
                    result.add(user.toFollowResp());
                }
            }
        }
        FolloweeResp followee = new FolloweeResp();
        followee.setUsers(result);
        followee.setFollowing(result.size());
        return followee;
    }

    /*
    @Transactional
    public void UpdateUser(UserUpdateReq req, String originalMail) {
        LocalDate thistime = LocalDate.now();
        User user = userRepository.findByEmail(originalMail).orElse(null);
        if (user != null) {
            user.setName(req.getName());
            user.setEmail(req.getEmail());
            user.setPassword(passwordEncoder.encode(req.getPassword()));
            user.setProfileImage(req.getImage());
            user.setUpdatedAt(thistime);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
    */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        return user.orElse(null);
    }
}
