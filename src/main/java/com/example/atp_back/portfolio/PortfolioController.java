package com.example.atp_back.portfolio;

import com.example.atp_back.portfolio.model.request.PortfolioCreateReqDto;
import com.example.atp_back.portfolio.model.response.PortfolioInstanceRespDto;
import com.example.atp_back.portfolio.model.response.PortfolioPageRespDto;
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
    public ResponseEntity<PortfolioPageRespDto> list(int page, int size) {
        PortfolioPageRespDto response = portfolioService.list(page, size);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{portfolioIdx}")
    public ResponseEntity<PortfolioInstanceRespDto> read(@PathVariable Long portfolioIdx) {
        PortfolioInstanceRespDto response = portfolioService.read(portfolioIdx);

        return ResponseEntity.ok(response);
    }
}
