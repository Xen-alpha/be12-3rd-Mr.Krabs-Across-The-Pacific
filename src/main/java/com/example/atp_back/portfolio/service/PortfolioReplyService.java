package com.example.atp_back.portfolio.service;

import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.portfolio.model.entity.PortfolioReply;
import com.example.atp_back.portfolio.model.request.PortfolioReplyReq;
import com.example.atp_back.portfolio.repository.PortfolioReplyRepository;
import com.example.atp_back.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PortfolioReplyService {
  private final PortfolioReplyRepository portfolioReplyRepository;

  /* 포트폴리오 댓글 관련 */
  public Long registerReply(PortfolioReplyReq dto, User user, Long portfolioIdx) {

    PortfolioReply portfolioReply= portfolioReplyRepository.save(dto.toEntity(user, Portfolio.builder().idx(portfolioIdx).build()));
    return portfolioReply.getIdx();
  }
}
