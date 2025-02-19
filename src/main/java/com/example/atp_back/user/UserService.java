package com.example.atp_back.user;

import com.example.atp_back.user.model.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserTierRepository userTierRepository;

    @Value("${filepath.default}")
    private String defaultFilePath;

    public String uploadUserImage(MultipartFile file) {
        // TODO: defaultFilePath + "/images/avatar/" + randomPath + "_" + file.getOriginalFilename() 에 파일 저장
        String randomPath = UUID.randomUUID().toString();
        return "/images/avatar/" + randomPath + "_" + file.getOriginalFilename();
    }

    @Transactional
    public void RegisterUser(SignupReq signupReq, MultipartFile file) {
        LocalDate thistime = LocalDate.now();
        UserTier initialTier = userTierRepository.findByGrade("Bronze").orElse(null);
        // TODO: File upload 위치 결정하고 업로드 함수 삽입
        String path = null;
        if (!file.isEmpty()){
            path = uploadUserImage(file);
        }
        User user = User.builder().name(signupReq.getName()).email(signupReq.getEmail())
                .password(passwordEncoder.encode(signupReq.getPassword()))
                .profileImage(path) // TODO
                .createdAt(thistime)
                .updatedAt(thistime)
                .role("ROLE_USER")
                .tierGrade(initialTier)
                .build();
        userRepository.save(user);
    }

    @Transactional
    public void UpdateUser(UserUpdateReq req, MultipartFile file) {
        LocalDate thistime = LocalDate.now();
        User user = userRepository.findByEmail(req.getEmail()).orElse(null);
        if (user != null) {
            user.setName(req.getName());
            user.setEmail(req.getEmail());
            user.setPassword(passwordEncoder.encode(req.getPassword()));
            user.setUpdatedAt(thistime);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        return user.orElse(null);
    }
}
