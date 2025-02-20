package com.example.atp_back.portfolio;

import com.example.atp_back.common.BaseResponse;
import com.example.atp_back.portfolio.model.request.PortfolioCreateReqDto;
import com.example.atp_back.portfolio.model.response.PortfolioInstanceResp;
import com.example.atp_back.portfolio.model.response.PortfolioListResp;
import com.example.atp_back.portfolio.model.response.PortfolioPageResp;
import com.example.atp_back.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/portfolio")
public class PortfolioController {
  private final PortfolioService portfolioService;

  @PostMapping("/register")
  public ResponseEntity<BaseResponse<Void>> register(@AuthenticationPrincipal User user, @RequestBody PortfolioCreateReqDto dto) {
    portfolioService.register(user, dto);

    BaseResponse<Void> resp = new BaseResponse<>();
    resp.success(null);

    return ResponseEntity.ok(resp);
  }

  @GetMapping("/list")
  public ResponseEntity<BaseResponse<PortfolioPageResp>> list(int page, int size) {
    BaseResponse<PortfolioPageResp> resp = new BaseResponse<>();
    resp.success(portfolioService.list(page, size));

    return ResponseEntity.ok(resp);
  }

  @GetMapping("/{portfolioIdx}")
  public ResponseEntity<BaseResponse<PortfolioInstanceResp>> read(@PathVariable Long portfolioIdx) {
    BaseResponse<PortfolioInstanceResp> resp = new BaseResponse<>();
    resp.success(portfolioService.read(portfolioIdx));
    return ResponseEntity.ok(resp);
  }

  //포트폴리오 검색
  //포트폴리오 이름을 기준으로 검색
  @GetMapping("/search/pname")
  public ResponseEntity<BaseResponse<PortfolioListResp>> searchByPName(String name) {
    BaseResponse<PortfolioListResp> resp = new BaseResponse<>();
    resp.success(portfolioService.searchByPName(name));
    return ResponseEntity.ok(resp);
  }

  //포트폴리오 작성자 기준으로 검색

  @GetMapping("/search/uname")
  public ResponseEntity<BaseResponse<PortfolioListResp>> searchByUName(String name) {
    BaseResponse<PortfolioListResp> resp = new BaseResponse<>();
    resp.success(portfolioService.searchByUName(name));
    return ResponseEntity.ok(resp);
  }

  //포트폴리오에 포함된 주식 기준으로 검색
  @GetMapping("/search/sname")
  public ResponseEntity<BaseResponse<PortfolioListResp>> searchBySName(String name) {
    BaseResponse<PortfolioListResp> resp = new BaseResponse<>();
    resp.success(portfolioService.searchBySName(name));
    return ResponseEntity.ok(resp);
  }


}
