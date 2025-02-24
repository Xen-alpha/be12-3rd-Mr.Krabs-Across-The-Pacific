package com.example.atp_back.portfolio;

import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
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

@Repository
@RequiredArgsConstructor
public class PortfolioCustomRepositoryImpl implements PortfolioCustomRepository {
  private final JPAQueryFactory queryFactory;

  @Override
  public List<Portfolio> findAllByNameContaining(String name) {
    List<Tuple> results = queryFactory
        .select(portfolio, bookmark.count())
        .from(portfolio)
        .leftJoin(bookmark).on(bookmark.portfolio.eq(portfolio))
        .where(portfolio.name.contains(name))
        .groupBy(portfolio)
        .orderBy(bookmark.count().desc())
        .fetch();

    return results.stream()
        .map(tuple -> tuple.get(portfolio))
        .collect(Collectors.toList());
  }

  @Override
  public List<Portfolio> findAllByUserNameContaining(String name) {
    List<Tuple> results = queryFactory
        .select(portfolio, bookmark.count())
        .from(portfolio)
        .leftJoin(portfolio.user, user)
        .leftJoin(bookmark).on(bookmark.portfolio.eq(portfolio))
        .where(user.name.contains(name))
        .groupBy(portfolio)
        .orderBy(bookmark.count().desc())
        .fetch();

    return results.stream()
        .map(tuple -> tuple.get(portfolio))
        .collect(Collectors.toList());
  }

  @Override
  public List<Portfolio> findAllByStockNameContaining(String name) {
    List<Tuple> results = queryFactory
        .select(portfolio, bookmark.count())
        .from(portfolio)
        .leftJoin(acquisition).on(acquisition.portfolio.eq(portfolio))
        .leftJoin(acquisition.stock, stock)
        .leftJoin(bookmark).on(bookmark.portfolio.eq(portfolio))
        .where(stock.name.contains(name))
        .groupBy(portfolio)
        .orderBy(bookmark.count().desc())
        .fetch();

    return results.stream()
        .map(tuple -> tuple.get(portfolio))
        .collect(Collectors.toList());
  }

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
}