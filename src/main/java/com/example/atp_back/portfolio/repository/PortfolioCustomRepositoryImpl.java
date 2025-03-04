package com.example.atp_back.portfolio.repository;

import com.example.atp_back.portfolio.model.entity.Portfolio;
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


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.atp_back.portfolio.model.entity.QAcquisition.acquisition;
import static com.example.atp_back.portfolio.model.entity.QBookmark.bookmark;
import static com.example.atp_back.portfolio.model.entity.QPortfolio.portfolio;
import static com.example.atp_back.stock.model.QStock.stock;
import static com.example.atp_back.user.model.QUser.user;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;


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

  //북마크 순서대로 정렬하여 포트폴리오 가져오기
  @Override
  public Page<Portfolio> findAllByOrderByBookmarksDesc(Pageable pageable) {
    List<Tuple> results = queryFactory
            .select(portfolio, bookmark.count())
            .from(portfolio)
            .leftJoin(bookmark).on(bookmark.portfolio.eq(portfolio))
            .groupBy(portfolio)
            .orderBy(bookmark.count().desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

    List<Portfolio> portfolios = results.stream()
            .map(tuple -> tuple.get(portfolio))
            .collect(Collectors.toList());

    // 전체 개수를 가져와서 Page 객체를 생성
    long total = Optional.ofNullable(queryFactory
            .select(portfolio.count())
            .from(portfolio)
            .fetchOne()).orElse(0L);

    return new PageImpl<>(portfolios, pageable, total);
  }

  // 포트폴리오 ID 목록 추출
  public List<Long> portfolioIds(List<Tuple> portfolioList){
    return portfolioList.stream()
            .map(tuple -> tuple.get(portfolio.idx))
            .collect(Collectors.toList());
  }

  //북마크 순서대로 정렬하여 포트폴리오 가져오기2
  @Override
  public Page<PortfolioInstanceResp> findAllByOrderByBookmarksDesc2(Pageable pageable) {
    //북마크 수에 따라서 정렬한 포트폴리오 목록을 페이지 정보를 이용해 잘라서 가져오기
    List<Tuple> portfolioList = queryFactory
            .select(portfolio.idx, portfolio.name, portfolio.imageUrl, portfolio.viewCnt, bookmark.count())
            .from(portfolio)
            .leftJoin(bookmark).on(bookmark.portfolio.eq(portfolio))
            .orderBy(bookmark.count().desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

    // 포트폴리오 ID 목록 추출
    List<Long> portfolioIds = portfolioIds(portfolioList);

    // 해당 포트폴리오 ID 목록에 속하는 Acquisition & Stock 조회
    List<Tuple> acquisitionList = queryFactory
            .select(acquisition.portfolio.idx, stock.idx, stock.name, acquisition.price, acquisition.quantity)
            .from(acquisition)
            .join(stock).on(acquisition.stock.eq(stock)).fetchJoin()
            .where(acquisition.portfolio.idx.in(portfolioIds))
            .fetch();

    List<Long> bookmarkList = queryFactory
            .select(bookmark.user.idx)
            .from(bookmark)
            .where(bookmark.portfolio.idx.in(portfolioIds))
            .fetch();

    List<AcquisitionInstanceResp> acquisitionListDto = acquisitionList.stream()
            .map(tuple -> AcquisitionInstanceResp.builder()
                    .portfolioIdx(tuple.get(acquisition.portfolio.idx))
                    .stockIdx(tuple.get(acquisition.stock.idx))
                    .stockName(tuple.get(stock.name))
                    .price(tuple.get(acquisition.price))
                    .quantity(tuple.get(acquisition.quantity))
                    .build()
            ).toList();

    List<PortfolioInstanceResp> result = portfolioList.stream()
            .map(tuple -> PortfolioInstanceResp.builder()
                    .idx(tuple.get(portfolio.idx))
                    .name(tuple.get(portfolio.name))
                    .imageUrl(tuple.get(portfolio.imageUrl))
                    .viewCnt(tuple.get(portfolio.viewCnt))
                    .bookmarkCnt(Math.toIntExact(tuple.get(bookmark.count())))
                    .bookmarkUsers(bookmarkList)
                    .acquisitionList(acquisitionListDto)
                    .build()
            ).toList();

    return new PageImpl<>(result, pageable, result.size());
  }

  //조회수 증가 쿼리
  @Override
  @Transactional
  public void incrementViewCnt(Long portfolioIdx) {
    //QPortfolio portfolio = QPortfolio.portfolio;

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