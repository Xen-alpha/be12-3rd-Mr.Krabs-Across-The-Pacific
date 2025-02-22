package com.example.atp_back.portfolio;

import com.example.atp_back.common.BaseResponse;
import com.example.atp_back.portfolio.model.request.PortfolioCreateReqDto;
import com.example.atp_back.portfolio.model.response.PortfolioInstanceResp;
import com.example.atp_back.portfolio.model.response.PortfolioListResp;
import com.example.atp_back.portfolio.model.response.PortfolioPageResp;
import com.example.atp_back.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/portfolio")
public class PortfolioController {
  private final PortfolioService portfolioService;

  @Operation(summary = "포트폴리오 등록", description = "포트폴리오를 등록하는 기능")
  @PostMapping("/register")
  public ResponseEntity<BaseResponse<Void>> register(@AuthenticationPrincipal User user, @RequestBody PortfolioCreateReqDto dto) {
    portfolioService.register(user, dto);

    BaseResponse<Void> resp = new BaseResponse<>();
    resp.success(null);

    return ResponseEntity.ok(resp);
  }

  @Operation(summary = "포트폴리오 목록 조회", description = "페이지별 포트폴리오 목록을 조회하고, 조회수, 북마크 수, 생성 날짜를 기준으로 정렬")
  @GetMapping("/list")
  public ResponseEntity<BaseResponse<PortfolioPageResp>> list(
          @PageableDefault(page = 0, size = 15, sort = "viewCnt") Pageable pageable) {
    BaseResponse<PortfolioPageResp> resp = new BaseResponse<>();
    resp.success(portfolioService.list(pageable));

    return ResponseEntity.ok(resp);
  }

  @Operation(summary = "포트폴리오 상세 조회", description = "포트폴리오의 Idx 값을 이용해 포트폴리오의 상세 내용을 확인하는 기능")
  @GetMapping("/{portfolioIdx}")
  public ResponseEntity<BaseResponse<PortfolioInstanceResp>> read(@AuthenticationPrincipal User user, @PathVariable Long portfolioIdx) {
    portfolioService.viewCnt(user, portfolioIdx);
    BaseResponse<PortfolioInstanceResp> resp = new BaseResponse<>();
    resp.success(portfolioService.read(portfolioIdx));
    return ResponseEntity.ok(resp);
  }

  @Operation(summary = "포트폴리오 검색", description = "사용자가 입력한 단어가 포트폴리오의 이름과 같거나 포함된 포트폴리오 목록을 조회")
  @GetMapping("/search/pname")
  public ResponseEntity<BaseResponse<PortfolioListResp>> searchByPName(String name) {
    BaseResponse<PortfolioListResp> resp = new BaseResponse<>();
    resp.success(portfolioService.searchByPName(name));
    return ResponseEntity.ok(resp);
  }
  
  @Operation(summary = "포트폴리오 검색", description = "사용자가 입력한 단어가 다른 사용자의 이름과 같거나 포함된 포트폴리오 목록을 조회.")
  @GetMapping("/search/uname")
  public ResponseEntity<BaseResponse<PortfolioListResp>> searchByUName(String name) {
    BaseResponse<PortfolioListResp> resp = new BaseResponse<>();
    resp.success(portfolioService.searchByUName(name));
    return ResponseEntity.ok(resp);
  }

  @Operation(summary = "포트폴리오 검색", description = "사용자가 입력한 단어가 주식 이름과 같거나 포함된 포트폴리오 목록을 조회.")
  @GetMapping("/search/sname")
  public ResponseEntity<BaseResponse<PortfolioListResp>> searchBySName(String name) {
    BaseResponse<PortfolioListResp> resp = new BaseResponse<>();
    resp.success(portfolioService.searchBySName(name));
    return ResponseEntity.ok(resp);
  }

}
