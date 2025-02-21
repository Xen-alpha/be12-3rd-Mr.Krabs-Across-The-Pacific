package com.example.atp_back.user;

import com.example.atp_back.common.BaseResponse;
import com.example.atp_back.user.model.SignupReq;
import com.example.atp_back.user.model.follow.FollowResp;
import com.example.atp_back.user.model.follow.FolloweeResp;
import com.example.atp_back.user.model.follow.FollowerResp;
import com.example.atp_back.user.model.follow.UserFollowReq;
import com.example.atp_back.user.model.UserInfoResp;
import com.example.atp_back.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Tag(name="회원 기능", description = "회원 가입, 업데이트, 탈퇴 등 관련 기능들")
public class UserController {
    private final UserService userService;

    @Operation(summary="회원가입", description = "회원 가입을 합니다")
    @ApiResponse(responseCode="200", description="정상가입, 성공 문자열을 반환합니다.")
    @ApiResponse(responseCode="400", description="가입 실패")
    @ApiResponse(responseCode="500", description="서버 내 오류")
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<String>> signup(
            @Parameter(description="SignupReq 데이터 전송 객체를 사용합니다")
            @Valid
            @RequestBody SignupReq request) {
        BaseResponse<String> resp = new BaseResponse<String>();
        userService.RegisterUser(request);
        resp.success("성공");
        return ResponseEntity.ok(resp);
    }

    @Operation(summary="유저 정보 조회", description = "자신의 유저 정보를 가져옵니다")
    @ApiResponse(responseCode="200", description="정상적으로 반환하였습니다")
    @ApiResponse(responseCode="403", description="허가되지 않은 유저 정보 조회 행위입니다")
    @GetMapping("/mypage")
    public ResponseEntity<BaseResponse<UserInfoResp>> getUserInformation(@CookieValue(name="ATOKEN", required = true) String token) {
        String userEmail = JwtUtil.getUserEmailFromToken(token);
        UserInfoResp result = userService.getUserInfo(userEmail);
        BaseResponse<UserInfoResp> resp = BaseResponse.<UserInfoResp>builder().isSuccess(true).result(result).build();
        return ResponseEntity.ok(resp);
    }

    // TODO: 자기 자신을 팔로우하는 건 예외 처리
    @PostMapping("/follow")
    public ResponseEntity<BaseResponse<String>> follow(
            @Parameter(description="UserFollowReq 데이터 전송 객체를 사용합니다")
            @Valid
            @RequestBody UserFollowReq reqBody,
            @CookieValue(name="ATOKEN", required = true) String token) {
        String requestUserMail = JwtUtil.getUserEmailFromToken(token);
        userService.follow(reqBody.getEmail(), requestUserMail);
        BaseResponse<String> result = new BaseResponse<>();
        result.success("팔로우 성공");
        return ResponseEntity.ok(result);
    }
    @Operation(description="나를 팔로우 중인 사람 조회")
    @GetMapping("/follower")
    public ResponseEntity<BaseResponse<FollowerResp>> getFollowers(@CookieValue(name="ATOKEN", required = true) String token) {
        String userEmail = JwtUtil.getUserEmailFromToken(token);
        List<FollowResp> followers = userService.getFollowers(userEmail);
        FollowerResp result = new FollowerResp(followers.size(), followers);
        BaseResponse<FollowerResp> response = new BaseResponse<>();
        response.success(result);
        return ResponseEntity.ok(response);
    }
    @Operation(description="내가 팔로우 중인 사람 조회")
    @GetMapping("/followee")
    public ResponseEntity<BaseResponse<FolloweeResp>> getFollowees(@CookieValue(name="ATOKEN", required = true) String token) {
        String userEmail = JwtUtil.getUserEmailFromToken(token);
        List<FollowResp> followees = userService.getFollowees(userEmail);
        FolloweeResp result = new FolloweeResp(followees.size(), followees);
        BaseResponse<FolloweeResp> response = new BaseResponse<>();
        response.success(result);
        return ResponseEntity.ok(response);
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

    @Operation(description="로그아웃 리다이렉션용")
    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<String>> successfulLogout() {
        BaseResponse<String> resp = new BaseResponse<>();
        resp.success("로그아웃에 성공했습니다");
        return ResponseEntity.ok(resp);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<BaseResponse<String>> customHandler(Exception e) {
        BaseResponse<String> response = new BaseResponse<String>();
        if (e.getMessage().equals("Failed to Follow")) {
            response.error("10304", "사용자 팔로우에 실패했습니다.");
            return ResponseEntity.badRequest().body(response);
        } else if (e.getMessage().equals("Failed to Unfollow")) {
            response.error("10305", "사용자 언팔로우에 실패했습니다.");
            return ResponseEntity.badRequest().body(response);
        }
        response.error("10101", "유저 정보를 불러올 수 없습니다");
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<BaseResponse<String>> badRequestHandler(Exception e) {
        BaseResponse<String> response = new BaseResponse<String>();
        response.error("10101", "유저 정보를 불러올 수 없습니다");
        return ResponseEntity.badRequest().body(response);
    }
}