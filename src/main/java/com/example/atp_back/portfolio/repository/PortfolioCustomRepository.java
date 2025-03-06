package com.example.atp_back.portfolio.repository;

import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.portfolio.model.entity.PortfolioReply;
import com.example.atp_back.portfolio.model.response.PortfolioInstanceResp;
import com.example.atp_back.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface PortfolioCustomRepository {
  //포트폴리오 검색
  Page<PortfolioInstanceResp> searchAllByKeyword(Pageable pageable, String keyword);

  //메인 페이지에서 포트폴리오 목록 조회
  Page<PortfolioInstanceResp> findAllByOrderByKeyword(Pageable pageable, String keyword);

  //포트폴리오 클릭시 조회수 증가
  void incrementViewCnt(Long portfolioIdx);

  //포트폴리오 수정 페이지를 위한 구매목록 전체 출력
  Portfolio findWithAcquisitionsById(Long idx);

}
