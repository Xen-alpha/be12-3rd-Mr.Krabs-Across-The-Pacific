package com.example.atp_back.common.code.status;

import com.example.atp_back.common.code.BaseErrorCode;
import com.example.atp_back.common.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "접근 권한이 없습니다."),
    _NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404", "요청하신 리소스를 찾을 수 없습니다."),


    // 멤버 관련 에러
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "사용자가 없습니다."),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4002", "필수 정보가 누락되었습니다."),
    MEMBER_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "MEMBER4003", "사용자 정보 갱신에 실패했습니다"),
    MEMBER_FOLLOW_FAILED(HttpStatus.BAD_REQUEST, "MEMBER4004", "사용자 팔로우에 실패했습니다"),
    MEMBER_UNFOLLOW_FAILED(HttpStatus.BAD_REQUEST, "MEMBER4005", "사용자 언팔로우에 실패했습니다"),
    MEMBER_FOLLOW_SAME_PERSON(HttpStatus.BAD_REQUEST, "MEMBER4006", "스스로 팔로우할 수 없습니다"),
    MEMBER_UNFOLLOW_SAME_PERSON(HttpStatus.BAD_REQUEST, "MEMBER4007", "스스로 언팔로우할 수 없습니다"),
    MEMBER_ALREADY_FOLLOWED(HttpStatus.BAD_REQUEST, "MEMBER4008", "이미 팔로우한 사용자입니다"),
    MEMBER_ALREADY_UNFOLLOWED(HttpStatus.BAD_REQUEST, "MEMBER4009", "이미 언팔로우한 사용자입니다"),
    MEMBER_DELETE_FAILED(HttpStatus.BAD_REQUEST, "MEMBER4010", "탈퇴에 실패했습니다"),

    // 포트폴리오 관련 에러
    PORTFOLIO_NOT_FOUND(HttpStatus.NOT_FOUND, "PORTFOLIO4001", "게시글이 없습니다."),

    //주식 관련 에러

    STOCK_GRAPH_NOT_FOUND(HttpStatus.NOT_FOUND, "STOCK4041", "그래프 데이터를 불러올 수 없습니다."),
    STOCK_RECENT_NOT_FOUND(HttpStatus.NOT_FOUND, "STOCK4042", "최근 가격을 불러올 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
