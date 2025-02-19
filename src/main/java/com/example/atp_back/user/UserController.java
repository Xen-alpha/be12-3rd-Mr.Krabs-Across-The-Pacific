package com.example.atp_back.user;

import com.example.atp_back.user.model.SignupReq;
import com.example.atp_back.user.model.SignupResp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<SignupResp> signup(@RequestPart SignupReq request, @RequestPart MultipartFile file) {
        SignupResp resp = new SignupResp();
        userService.RegisterUser(request, file);
        resp.setIsSuccess(true);
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/update")
    public ResponseEntity<SignupResp> update(@RequestPart SignupReq request, @RequestPart MultipartFile file) {
        SignupResp resp = new SignupResp();
        userService.UpdateUser(request, file);
        resp.setIsSuccess(true);
        return ResponseEntity.ok(resp);
    }

}