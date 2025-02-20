package com.example.atp_back.portfolio;

import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.portfolio.model.request.PortfolioCreateReqDto;
import com.example.atp_back.portfolio.model.response.PortfolioInstanceResp;
import com.example.atp_back.portfolio.model.response.PortfolioListResp;
import com.example.atp_back.portfolio.model.response.PortfolioPageResp;
import com.example.atp_back.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Port;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    public void register(User user, PortfolioCreateReqDto dto) {
        Portfolio portfolio = portfolioRepository.save(dto.toEntity(user));
    }

    public PortfolioPageResp list(int page, int size) {
        Page<Portfolio> result = portfolioRepository.findAll(PageRequest.of(page, size));
        return PortfolioPageResp.from(result);
    }

    public PortfolioInstanceResp read(Long portfolioIdx) {
        Portfolio portfolio = portfolioRepository.findById(portfolioIdx).orElseThrow();
        return PortfolioInstanceResp.from(portfolio);
    }

  public PortfolioListResp searchByPName(String name) {
      List<Portfolio> portfolioList = portfolioRepository.findAllByNameContaining(name);
      return PortfolioListResp.from(portfolioList);
  }

  public PortfolioListResp searchByUName(String name) {
      List<Portfolio> portfolioList = portfolioRepository.findAllByUserNameContaining(name);
      return PortfolioListResp.from(portfolioList);
  }

  public PortfolioListResp searchBySName(String name) {
      List<Portfolio> portfolioList = portfolioRepository.findAllByStockNameContaining(name);
      return PortfolioListResp.from(portfolioList);
    }
}