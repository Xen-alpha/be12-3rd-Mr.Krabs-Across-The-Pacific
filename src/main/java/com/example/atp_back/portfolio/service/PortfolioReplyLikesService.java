package com.example.atp_back.portfolio.service;

import com.example.atp_back.portfolio.model.entity.PortfolioReply;
import com.example.atp_back.portfolio.model.entity.PortfolioReplyLikes;
import com.example.atp_back.portfolio.repository.PortfolioReplyLikesRepository;
import com.example.atp_back.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PortfolioReplyLikesService {
  private final PortfolioReplyLikesRepository portfolioReplyLikesRepository;

  /* 포트폴리오 댓글 좋아요 관련*/
  public Long likesReply(User user, Long portfolioReplyIdx) {

    PortfolioReplyLikes portfolioReplyLikes = portfolioReplyLikesRepository.save(
        PortfolioReplyLikes.builder()
            .user(user)
            .reply(PortfolioReply.builder().idx(portfolioReplyIdx).build())
            .build());
    return portfolioReplyLikes.getIdx();
  }
}
