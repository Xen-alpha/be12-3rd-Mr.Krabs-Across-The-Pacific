package com.example.atp_back.portfolio;

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
    public void register(@AuthenticationPrincipal User user, @RequestBody PortfolioCreateReqDto dto) {
        portfolioService.register(user, dto);
    }

    @GetMapping("/list")
    public ResponseEntity<PortfolioPageResp> list(int page, int size) {
        PortfolioPageResp response = portfolioService.list(page, size);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{portfolioIdx}")
    public ResponseEntity<PortfolioInstanceResp> read(@PathVariable Long portfolioIdx) {
        PortfolioInstanceResp response = portfolioService.read(portfolioIdx);

        return ResponseEntity.ok(response);
    }
}
