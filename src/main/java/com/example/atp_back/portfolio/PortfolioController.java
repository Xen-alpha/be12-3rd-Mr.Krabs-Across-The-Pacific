package com.example.atp_back.portfolio;

import com.example.atp_back.common.BaseResponse;
import com.example.atp_back.portfolio.model.request.PortfolioCreateReqDto;
import com.example.atp_back.portfolio.model.response.PortfolioInstanceResp;
import com.example.atp_back.portfolio.model.response.PortfolioPageResp;
import com.example.atp_back.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
}
