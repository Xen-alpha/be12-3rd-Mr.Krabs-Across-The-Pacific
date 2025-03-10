package com.example.atp_back.portfolio.service;

import com.example.atp_back.common.code.status.ErrorStatus;
import com.example.atp_back.common.exception.handler.PortfolioHandler;
import com.example.atp_back.common.exception.handler.UserHandler;
import com.example.atp_back.portfolio.model.entity.*;
import com.example.atp_back.portfolio.model.request.PortfolioCreateReqDto;
import com.example.atp_back.portfolio.model.response.*;
import com.example.atp_back.portfolio.repository.*;
import com.example.atp_back.stock.model.Stock;
import com.example.atp_back.stock.model.StockGraphDocument;
import com.example.atp_back.stock.repository.StockGraphRepository;
import com.example.atp_back.stock.repository.StockRepository;
import com.example.atp_back.user.model.User;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Port;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final BadgeRepository badgeRepository;
    private final RewardRepository rewardRepository;
    private final BookmarkRepository bookmarkRepository;
    private final PortfolioReplyService portfolioReplyService;
    private final AcquisitionRepository acquisitionRepository;
    private final StockRepository stockRepository;
    private final StockGraphRepository stockGraphRepository;
    private final MongoTemplate mongoTemplate;

    @Transactional
    public Long register(User user, PortfolioCreateReqDto dto) {
        Portfolio portfolio = portfolioRepository.save(dto.toEntity(user));
        dto.getAcquisitionList().forEach(acquisition -> {
            Stock stock = stockRepository.findByCode(acquisition.getStockCode()).orElseThrow(()-> new PortfolioHandler(ErrorStatus._BAD_REQUEST));
            acquisitionRepository.save(acquisition.toEntity(stock, portfolio));
        });
        return portfolio.getIdx();
    }

    public PortfolioPageResp list(@Nullable User user, Pageable pageable) {
        //메인 페이지에 보여줄 정렬 조건
        String sortBy = pageable.getSort().stream().findFirst().map(Sort.Order::getProperty).orElse("View");
        return PortfolioPageResp.from(user, portfolioRepository.findAllByOrderByKeyword(pageable, sortBy));
    }

    public PortfolioPageResp listByIdx(@Nullable User user, Pageable pageable, Long userIdx) {
      String sortBy = pageable.getSort().stream().findFirst().map(Sort.Order::getProperty).orElse("View");
      return PortfolioPageResp.from(user, portfolioRepository.findAllByOrderByUserId(pageable, sortBy, userIdx));
    }

    public PortfolioInstanceResp read(@Nullable User user, Long portfolioIdx) {
        //포트폴리오 idx를 이용해서 acquisition 목록 반환
        Portfolio portfolio = portfolioRepository.findWithAcquisitionsById(portfolioIdx);

        return PortfolioInstanceResp.fromDetail(user, portfolio);
    }

    /*포트폴리오 검색*/
    public PortfolioPageResp searchByKeyword(@Nullable User user, Pageable pageable, String keyword) {
        return PortfolioPageResp.from(user, portfolioRepository.searchAllByKeyword(pageable, keyword));
    }

    /*포트폴리오 조회수 관련*/
    @Transactional
    public void viewCnt(Long portfolioIdx) {
        //조회수 증가
        portfolioRepository.incrementViewCnt(portfolioIdx);

        Portfolio portfolio = portfolioRepository.findById(portfolioIdx).orElseThrow();
        int viewCnt = portfolio.getViewCnt();
        //조회수가 1000회 이상이면 1번 뱃지 부여
        if (viewCnt > 1000){ assignBadge(portfolio, 1); }
    }

    /*포트폴리오 북마크*/
    @Transactional
    public Boolean registerBookmark(User user, Long portfolioIdx, boolean bookmark) {
        Portfolio portfolio = portfolioRepository.findById(portfolioIdx).orElseThrow();
        if (!bookmark) {
            //북마크 추가
            Bookmark newBookmark = Bookmark.builder().user(user).portfolio(portfolio).build();
            bookmarkRepository.save(newBookmark);
            int bookmarkCnt = bookmarkRepository.countByPortfolioIdx(portfolioIdx);
            //북마크 수가 100개 이상이면 해당 포트폴리오에 2번 뱃지 부여
            if(bookmarkCnt > 100){ assignBadge(portfolio, 2); }
            return true;
        } else { //북마크 취소
            Optional<Bookmark> existingBookmark = bookmarkRepository.findByUserAndPortfolio(user, portfolio);
            existingBookmark.ifPresent(bookmarkRepository::delete);
            return false;
        }
    }

    @Transactional
    public void assignBadge(Portfolio portfolio, long badgeIdx) {
        Badge badge = badgeRepository.findById(badgeIdx).orElseThrow();

        // 이미 같은 Badge가 있으면 중복 방지
        boolean alreadyHasBadge = rewardRepository.existsByPortfolioAndBadge(portfolio, badge);
        if (alreadyHasBadge) { return; }

        //포트폴리오와 badge에 업데이트된 정보 저장
        portfolioRepository.save(Portfolio.builder().badges(portfolio.getBadges()+badge.getCode()).build());
        rewardRepository.save(Reward.builder().portfolio(portfolio).badge(badge).build());
    }

//    @Transactional
//    public void badgeToPortfolio(Portfolio portfolio){
//        //포트폴리오 생성 기간 계산
//        long portfolioSinceCreated = Duration.between(portfolio.getCreatedAt(), LocalDateTime.now()).toDays();
//        }
//        //TODO : 추후 수익률 구현되면 3번 뱃지 추가하기 (생성 기간과 수익률 정보 같이 적용)
//        if(portfolioSinceCreated > 30) {
//            assignBadge(portfolio, 3);
//        }
//    }

    @AllArgsConstructor
    @Getter
    private class Asset implements Comparable<Asset> {
        private String code;
        private Double price;
        private BigDecimal quantity;

        @Override
        public int compareTo(Asset o) {
            return code.compareTo(o.code);
        }
    }

    @Scheduled(fixedDelay = 86400000) // 하루에 한 번 정기적으로 실행
    @Transactional
    public void runProfitData() {
        // 먼저 주식 종목 수 싱크를 맞춘다.
        List<StockGraphDocument> newstocks = stockGraphRepository.findAllByDateBetweenOrderByCodeAsc(System.currentTimeMillis()-86400000*2, System.currentTimeMillis()-86400000*1).stream().sorted((o1, o2) -> o1.getCode().compareTo(o2.getCode())).toList();
        List<Stock> oldStocks = stockRepository.findAll();
        // 길이가 다르면 갱신한다
        List<Stock> newList = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i != oldStocks.size() && j != newstocks.size()) {
            if (i == oldStocks.size()) { // 맨 뒤에신규 상장만 남음
                StockGraphDocument newStock = newstocks.get(j);
                newList.add(Stock.builder().name(newStock.getName()).code(newStock.getCode()).market(newStock.getMarket()).build());
                j++;
            } else if (j == newstocks.size()) {
                i++;
            } else {
                Stock oldStock = oldStocks.get(i);
                StockGraphDocument newStock = newstocks.get(j);
                Integer compareResult = oldStock.getCode().compareTo(newStock.getCode());
                if (compareResult == 0) { //싱크가 맞다.
                    i++;
                    j++;
                } else if (compareResult < 0) { // 이 종목은 상폐를 당했다.
                    i++; // 인덱스를 옮기자
                } else { // 비교 대상인 된 새 티커 코드는 신규 상장 코드다.
                    newList.add(Stock.builder().name(newStock.getName()).code(newStock.getCode()).market(newStock.getMarket()).build());
                    j++; // 새 목록의 인덱스를 1 옮긴다.
                }
            }
        }
        stockRepository.saveAll(newList);
        // 포트폴리오 수익률 및 순위 갱신
        List<Portfolio> portfolios = portfolioRepository.findAll();
        for (Portfolio portfolio : portfolios) {
            // 수익률 계산을 위해 최근 가격을 가져옵니다.
            List<Asset> assets = portfolio.getAcquisitionList().stream().map(acquisition -> new Asset(acquisition.getStock().getCode(), acquisition.getPrice().doubleValue(), acquisition.getQuantity())).sorted().toList();
            // 여기서부터 MongoDB
            Double result = 0.0;
            for (i = 0 ; i < assets.size(); i++) { // 정렬은 이미 마친 상황
                StockGraphDocument recentprice = stockGraphRepository.findFirstByCodeOrderByDateDesc(assets.get(i).getCode()).orElse(null);
                Double currentprice = recentprice == null ? 0 : recentprice.getPrice();
                Double firstprice = assets.get(i).getPrice();
                result += (currentprice - firstprice) * assets.get(i).getQuantity().doubleValue();
            }
            portfolio.setProfit(result);
        }
        Collections.sort(portfolios, (o1, o2) -> o1.getProfit().compareTo(o2.getProfit()) * -1); // profit이 큰 순으로 정렬
        for (i = 1; i <= portfolios.size(); i++) {
            portfolios.get(i-1).setRatings(i);
            portfolioRepository.save(portfolios.get(i-1));
        }
    }

}
