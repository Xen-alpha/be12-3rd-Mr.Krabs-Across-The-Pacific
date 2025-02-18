package com.example.atp_back.user;

import com.example.atp_back.user.model.SignupReq;
import com.example.atp_back.user.model.SignupResp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping("/signup")
    public ResponseEntity<SignupResp> signup(@RequestPart SignupReq request, @RequestPart MultipartFile file) {
        SignupResp resp = new SignupResp();

        resp.setIsSuccess(true);
        return ResponseEntity.ok(resp);
    }
}
