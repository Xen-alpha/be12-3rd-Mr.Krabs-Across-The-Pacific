package com.example.atp_back.user;

import com.example.atp_back.user.model.SignupReq;
import com.example.atp_back.common.SuccessResp;
import com.example.atp_back.user.model.UserUpdateReq;
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
    public ResponseEntity<SuccessResp<String>> signup(@RequestBody SignupReq request) {
        SuccessResp<String> resp = new SuccessResp<String>();
        userService.RegisterUser(request);
        resp.setSuccess(true);
        resp.setResult("Signup Successful");
        return ResponseEntity.ok(resp);
    }
    /*
    @PutMapping("/update")
    public ResponseEntity<SuccessResp<String>> update(@RequestPart UserUpdateReq request, @RequestPart MultipartFile file) {
        SuccessResp<String> resp = new SuccessResp<>();
        // TODO: 명세를 변경하거나, JWT에 있는 기존 email 주소 데이터를 가져와야 함
        // userService.UpdateUser(request, file);
        resp.setSuccess(true);
        resp.setResult("Update Successful");
        return ResponseEntity.ok(resp);
    }
    */
}