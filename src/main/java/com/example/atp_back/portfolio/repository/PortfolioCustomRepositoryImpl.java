package com.example.atp_back.portfolio.repository;

import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.portfolio.model.entity.QPortfolio;
import com.example.atp_back.portfolio.model.response.AcquisitionInstanceResp;
import com.example.atp_back.portfolio.model.response.PortfolioInstanceResp;
import com.example.atp_back.portfolio.model.response.BadgeInstanceResp;
import com.example.atp_back.user.model.User;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
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
import static com.example.atp_back.portfolio.model.entity.QBadge.badge;
import static com.example.atp_back.portfolio.model.entity.QReward.reward;
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

  @Override
  @Transactional
  public void incrementViewCnt(Long portfolioIdx) {
    QPortfolio portfolio = QPortfolio.portfolio;

    queryFactory.update(portfolio)
            .set(portfolio.viewCnt, portfolio.viewCnt.add(1))
            .where(portfolio.idx.eq(portfolioIdx))
            .execute();
  }
  @Override
  public List<PortfolioInstanceResp> findPortfolioWithBadges(User user) {
    return queryFactory
            .selectFrom(portfolio)
            .leftJoin(portfolio.rewards, reward).fetchJoin()
            .leftJoin(reward.badge, badge).fetchJoin()
            .leftJoin(portfolio.bookmarkList, bookmark).fetchJoin()
            .leftJoin(portfolio.acquisitionList, acquisition).fetchJoin()
            .leftJoin(acquisition.stock, stock).fetchJoin()
            .transform(groupBy(portfolio.idx).list(
                    Projections.constructor(PortfolioInstanceResp.class,
                            portfolio.idx,
                            portfolio.name,
                            portfolio.imageUrl,
                            portfolio.viewCnt,

                            Expressions.asBoolean(user != null).and(bookmark.user.idx.eq(user.getIdx())),

                            list(Projections.constructor(AcquisitionInstanceResp.class,
                                    acquisition.idx,
                                    acquisition.price,
                                    acquisition.quantity,
                                    acquisition.orderAt,
                                    stock.name
                            )),

                            list(Projections.constructor(BadgeInstanceResp.class,
                                    badge.idx
                            ))
                    )
            ));
  }


}