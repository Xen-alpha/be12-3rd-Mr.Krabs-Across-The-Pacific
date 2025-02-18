package com.example.atp_back.user;

import com.example.atp_back.user.model.SignupReq;
import com.example.atp_back.user.model.SignupResp;
import com.example.atp_back.user.model.User;
import com.example.atp_back.user.model.UserTier;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    public void RegisterUser(SignupReq signupReq, MultipartFile file) {
        LocalDate thistime = LocalDate.now();
        User user = User.builder().name(signupReq.getName()).email(signupReq.getEmail())
                .password(signupReq.getPassword())
                .profileImage(signupReq.getImage())
                .createdAt(thistime)
                .updatedAt(thistime)
                .tierGrade(UserTier.builder().grade("Bronze").build())
                .build();
        userRepository.save(user);
        // TODO: File upload
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        return user.orElse(null);
    }
}
