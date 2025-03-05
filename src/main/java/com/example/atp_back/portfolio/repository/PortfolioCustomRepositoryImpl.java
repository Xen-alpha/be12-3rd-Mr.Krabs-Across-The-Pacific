package com.example.atp_back.portfolio.repository;

import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.portfolio.model.entity.QBookmark;
import com.example.atp_back.portfolio.model.entity.QPortfolio;
import com.example.atp_back.portfolio.model.response.AcquisitionInstanceResp;
import com.example.atp_back.portfolio.model.response.PortfolioInstanceResp;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.atp_back.portfolio.model.entity.QAcquisition.acquisition;
import static com.example.atp_back.portfolio.model.entity.QBookmark.bookmark;
import static com.example.atp_back.portfolio.model.entity.QPortfolio.portfolio;
import static com.example.atp_back.stock.model.QStock.stock;
import static com.example.atp_back.user.model.QUser.user;


@Repository
@RequiredArgsConstructor
public class PortfolioCustomRepositoryImpl implements PortfolioCustomRepository {
  private final JPAQueryFactory queryFactory;

  @Override
  public List<Portfolio> searchAllByKeyword(String keyword) {
    List<Tuple> results = queryFactory
        .select(portfolio, bookmark.count())
        .from(portfolio)
        .leftJoin(portfolio.user, user)
        .leftJoin(acquisition).on(acquisition.portfolio.eq(portfolio))
        .leftJoin(acquisition.stock, stock)
        .leftJoin(bookmark).on(bookmark.portfolio.eq(portfolio))
        .where(
            portfolio.name.contains(keyword)
                .or(user.name.contains(keyword))
                .or(stock.name.contains(keyword))
        )
        .groupBy(portfolio)
        .orderBy(bookmark.count().desc())
        .fetch();

    return results.stream()
        .map(tuple -> tuple.get(portfolio))
        .collect(Collectors.toList());
  }

  // 포트폴리오 ID 목록 추출
  public List<Long> portfolioIds(List<Tuple> portfolioList){
    return portfolioList.stream()
            .map(tuple -> tuple.get(portfolio.idx))
            .collect(Collectors.toList());
  }

  // 포트폴리오 ID가 담긴 List가 주어질 때, 해당 포트폴리오에 해당하는 Acqusition의 정보를 Stock과 Join하여 AcquisitionInstanceResp 형태로 반영
  public List<AcquisitionInstanceResp> acquisitionList(List<Long> portfolioIds){
    List<Tuple> acquisitionList = queryFactory
        .select(acquisition.portfolio.idx, stock.idx, stock.name, acquisition.price, acquisition.quantity)
        .from(acquisition)
        .join(stock).on(acquisition.stock.eq(stock)).fetchJoin()
        .where(acquisition.portfolio.idx.in(portfolioIds))
        .fetch();

    return acquisitionList.stream()
        .map(tuple -> AcquisitionInstanceResp.builder()
            .portfolioIdx(tuple.get(acquisition.portfolio.idx))
            .stockIdx(tuple.get(acquisition.stock.idx))
            .stockName(tuple.get(stock.name))
            .price(tuple.get(acquisition.price))
            .quantity(tuple.get(acquisition.quantity))
            .build()
        ).toList();
  }

  //메인 페이지 포트폴리오 정렬 조건
  private OrderSpecifier<?> getSortedColumn(String sortBy) {
    if(sortBy.equals("View")) { return new OrderSpecifier<>(Order.DESC, portfolio.viewCnt); }
    else if(sortBy.equals("Bookmark")) { return new  OrderSpecifier<>(Order.DESC, bookmark.count() ); }
    else{ return new OrderSpecifier<>(Order.DESC, portfolio.createdAt); }
  }

  @Override
  public Page<PortfolioInstanceResp> findAllByOrderByKeyword(Pageable pageable, String keyword) {
    // 동적 정렬 조건 생성
    OrderSpecifier<?> orderSpecifier = getSortedColumn(keyword);

    List<Tuple> portfolioList = queryFactory
        .select(portfolio.idx, portfolio.name, portfolio.imageUrl, portfolio.viewCnt, bookmark.count(), portfolio.badges)
        .from(portfolio)
        .leftJoin(bookmark).on(bookmark.portfolio.eq(portfolio))
        .orderBy(orderSpecifier)
        .groupBy(portfolio)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    // 포트폴리오 ID 목록에 속하는 Acquisition & Stock 조회
    List<AcquisitionInstanceResp> acquisitionList = acquisitionList(portfolioIds(portfolioList));

    // 현재 로그인한 유저가 포트폴리오 북마크했는지 여부를 확인하기 위한 bookmarkList
    List<Long> bookmarkList = queryFactory
        .select(bookmark.user.idx)
        .from(bookmark)
        .where(bookmark.portfolio.idx.in(portfolioIds(portfolioList)))
        .fetch();

    List<PortfolioInstanceResp> result = portfolioList.stream()
        .map(tuple -> PortfolioInstanceResp.builder()
            .idx(tuple.get(portfolio.idx))
            .name(tuple.get(portfolio.name))
            .imageUrl(tuple.get(portfolio.imageUrl))
            .viewCnt(tuple.get(portfolio.viewCnt))
            .badges(tuple.get(portfolio.badges))
            .bookmarkCnt(Math.toIntExact(tuple.get(bookmark.count())))
            .bookmarkUsers(bookmarkList)
            .acquisitionList(acquisitionList)
            .build()
        ).toList();

    return new PageImpl<>(result, pageable, result.size());
  }

  //조회수 증가 쿼리
  @Override
  @Transactional
  public void incrementViewCnt(Long portfolioIdx) {
    queryFactory.update(portfolio)
            .set(portfolio.viewCnt, portfolio.viewCnt.add(1))
            .where(portfolio.idx.eq(portfolioIdx))
            .execute();
  }

  //포트폴리오 상세페이지에서 해당 포트폴리의 Idx와 일치하는 acquisition 정보를 가져오는 부분
  @Override
  public Portfolio findWithAcquisitionsById(Long idx) {
    return queryFactory
            .selectFrom(portfolio)
            .leftJoin(portfolio.acquisitionList, acquisition).fetchJoin()
            .leftJoin(acquisition.stock, stock).fetchJoin()
            .where(portfolio.idx.eq(idx))
            .fetchOne();
  }
}