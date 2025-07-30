package com.example.atp_back.portfolio.service;

import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.portfolio.model.entity.PortfolioReply;
import com.example.atp_back.portfolio.model.request.PortfolioReplyReq;
import com.example.atp_back.portfolio.model.response.PortfolioReplyInstanceResp;
import com.example.atp_back.portfolio.repository.PortfolioReplyRepository;
import com.example.atp_back.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Slice;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PortfolioReplyService {
  private final PortfolioReplyRepository portfolioReplyRepository;

  /* 포트폴리오 댓글 관련 */
  public Long registerReply(PortfolioReplyReq dto, User user, Long portfolioIdx) {

    PortfolioReply portfolioReply= portfolioReplyRepository.save(dto.toEntity(user, Portfolio.builder().idx(portfolioIdx).build()));
    return portfolioReply.getIdx();
  }

  public Slice<PortfolioReplyInstanceResp> getReplies(Long portfolioIdx, int page, int size) {
    // PortfolioReply 정보 가져오기
    Slice<PortfolioReply> replySlice = portfolioReplyRepository.findByPortfolioIdxOrderByCreatedAtDesc(
            portfolioIdx, PageRequest.of(page, size)
    );

    // PortfolioReplyInstanceResp로 변환
    List<PortfolioReplyInstanceResp> replyList = PortfolioReplyInstanceResp.from(replySlice.getContent());

    // 새로운 Slice 생성 (hasNext 정보 유지)
    return new SliceImpl<>(replyList, replySlice.getPageable(), replySlice.hasNext());
//    return portfolioReplyRepository.findByPortfolioIdxOrderByCreatedAtDesc(portfolioIdx, PageRequest.of(page, size));
  }
}
