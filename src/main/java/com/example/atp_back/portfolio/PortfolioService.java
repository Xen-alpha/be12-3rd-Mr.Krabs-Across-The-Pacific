package com.example.atp_back.portfolio;

import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.portfolio.model.entity.PortfolioReply;
import com.example.atp_back.portfolio.model.entity.PortfolioReplyLikes;
import com.example.atp_back.portfolio.model.request.PortfolioCreateReqDto;
import com.example.atp_back.portfolio.model.request.PortfolioReplyReq;
import com.example.atp_back.portfolio.model.response.PortfolioDetailResp;
import com.example.atp_back.portfolio.model.response.PortfolioListResp;
import com.example.atp_back.portfolio.model.response.PortfolioPageResp;
import com.example.atp_back.user.model.User;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final PortfolioReplyRepository portfolioReplyRepository;
    private final PortfolioReplyLikesRepository portfolioReplyLikesRepository;

    public Long register(User user, PortfolioCreateReqDto dto) {
        Portfolio portfolio = portfolioRepository.save(dto.toEntity(user));
        return portfolio.getIdx();
    }

    public PortfolioPageResp list(@Nullable User user, Pageable pageable) {
        String sortBy = pageable.getSort().stream()
                .findFirst()
                .map(Sort.Order::getProperty)
                .orElse("viewCnt");

        Page<Portfolio> result = null;
        if ("bookmarks".equals(sortBy)) {
            result = portfolioRepository.findAllByOrderByBookmarksDesc(pageable);
        } else if("createdAt".equals(sortBy)) {
            result = portfolioRepository.findAllByOrderByCreatedAtDesc(pageable);
        }else{
            result = portfolioRepository.findAllByOrderByViewCntDesc(pageable);
        }

        return PortfolioPageResp.from(user, result);
    }

    public PortfolioDetailResp read(@Nullable User user, Long portfolioIdx) {
        Portfolio portfolio = portfolioRepository.findById(portfolioIdx).orElseThrow();
        return PortfolioDetailResp.from(user, portfolio);
    }

    /*포트폴리오 검색 관련*/
  public PortfolioListResp searchByPName(String name) {
      List<Portfolio> portfolioList = portfolioRepository.findAllByNameContaining(name);
      return PortfolioListResp.from(null, portfolioList);
  }

  public PortfolioListResp searchByUName(String name) {
      List<Portfolio> portfolioList = portfolioRepository.findAllByUserNameContaining(name);
      return PortfolioListResp.from(null, portfolioList);
  }

  public PortfolioListResp searchBySName(String name) {
      List<Portfolio> portfolioList = portfolioRepository.findAllByStockNameContaining(name);
      return PortfolioListResp.from(null, portfolioList);
    }

    /* 포트폴리오 댓글 관련 */
    public Long registerReply(PortfolioReplyReq dto, User user, Long portfolioIdx) {

        PortfolioReply portfolioReply= portfolioReplyRepository.save(dto.toEntity(user, Portfolio.builder().idx(portfolioIdx).build()));
        return portfolioReply.getIdx();
    }

    /* 포트폴리오 댓글 좋아요 관련*/
    public Long likesReply(User user, Long portfolioReplyIdx) {

        PortfolioReplyLikes portfolioReplyLikes = portfolioReplyLikesRepository.save(
                PortfolioReplyLikes.builder()
                        .user(user)
                        .reply(PortfolioReply.builder().idx(portfolioReplyIdx).build())
                        .build());
        return portfolioReplyLikes.getIdx();
    }

  public void viewCnt(User user, Long portfolioIdx) {
  }
}