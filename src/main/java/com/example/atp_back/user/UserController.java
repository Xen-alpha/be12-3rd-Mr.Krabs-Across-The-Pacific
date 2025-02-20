package com.example.atp_back.user;

import com.example.atp_back.common.BaseResponse;
import com.example.atp_back.user.model.SignupReq;
import com.example.atp_back.user.model.UserUpdateReq;
import com.example.atp_back.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Tag(name="회원 기능", description = "회원 가입, 업데이트, 탈퇴 등 관련 기능들")
public class UserController {
    private final UserService userService;

    @Operation(summary="회원가입", description = "회원 가입을 합니다")
    @ApiResponse(responseCode="200", description="정상가입, 성공 문자열을 반환합니다.")
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<String>> signup(
            @Parameter(description="SignupReq 데이터 전송 객체를 사용합니다")
            @RequestBody SignupReq request) {
        BaseResponse<String> resp = new BaseResponse<String>();
        userService.RegisterUser(request);
        resp.success("성공");
        return ResponseEntity.ok(resp);
    }
    /*
    @Tag(name="회원 정보 업데이트", description = "회원 정보 업데이트를 합니다")
    @PutMapping("/update")
    public ResponseEntity<BaseResponse<String>> update(
            @Parameter(description="UserUpdateReq 데이터 전송 객체를 사용합니다")
            @RequestBody UserUpdateReq request,
            @Parameter(description="UserUpdateReq 데이터 전송 객체를 사용합니다")
            @CookieValue(name="ATOKEN", required=true) String token) {
        String originalMail = JwtUtil.getUserEmailFromToken(token);
        // TODO: originalMail == null일 때 예외처리 핸들러 추가할 것
        BaseResponse<String>resp = new BaseResponse<>();
        userService.UpdateUser(request, originalMail);
        resp.success("Update success");
        return ResponseEntity.ok(resp);
    }
    */
}