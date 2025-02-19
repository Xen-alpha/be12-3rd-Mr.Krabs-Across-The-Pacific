package com.example.atp_back.portfolio;

import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.portfolio.model.request.PortfolioCreateReqDto;
import com.example.atp_back.portfolio.model.response.PortfolioInstanceRespDto;
import com.example.atp_back.portfolio.model.response.PortfolioPageRespDto;
import com.example.atp_back.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    public void register(User user, PortfolioCreateReqDto dto) {
        Portfolio portfolio = portfolioRepository.save(dto.toEntity(user));
    }

    public PortfolioPageRespDto list(int page, int size) {
        Page<Portfolio> result = portfolioRepository.findAll(PageRequest.of(page, size));
        return PortfolioPageRespDto.from(result);
    }

    public PortfolioInstanceRespDto read(Long portfolioIdx) {
        Portfolio portfolio = portfolioRepository.findById(portfolioIdx).orElseThrow();
        return PortfolioInstanceRespDto.from(portfolio);
    }
}
