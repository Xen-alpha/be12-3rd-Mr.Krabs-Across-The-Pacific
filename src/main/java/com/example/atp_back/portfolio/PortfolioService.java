package com.example.atp_back.portfolio;

import com.example.atp_back.common.RedisDao;
import com.example.atp_back.portfolio.model.entity.*;
import com.example.atp_back.portfolio.model.request.PortfolioCreateReqDto;
import com.example.atp_back.portfolio.model.request.PortfolioReplyReq;
import com.example.atp_back.portfolio.model.response.PortfolioDetailResp;
import com.example.atp_back.portfolio.model.response.PortfolioInstanceResp;
import com.example.atp_back.portfolio.model.response.PortfolioListResp;
import com.example.atp_back.portfolio.model.response.PortfolioPageResp;
import com.example.atp_back.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final PortfolioReplyRepository portfolioReplyRepository;
    private final PortfolioReplyLikesRepository portfolioReplyLikesRepository;
    private final RedisDao redisDao;
    private final BadgeRepository badgeRepository;
    private final RewardRepository rewardRepository;
    private final AcquisitionRepository acquisitionRepository;

    @Transactional
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


    /*TODO : 포트폴리오 조회수 관련*/
    // 포트폴리오 조회수 관리 (실시간, 전체 분리)
    public void viewCnt(User user, Long portfolioIdx) {
        Portfolio portfolio = portfolioRepository.findById(portfolioIdx)
                .orElseThrow(() -> new IllegalArgumentException("포트폴리오를 찾을 수 없습니다."));

        String totalKey = "total:viewCount:" + portfolioIdx; // 전체 인기 포트폴리오 조회수 key
        String redisUserKey = "user:viewList:" + user.getUsername(); // 유저별 조회 이력 key

        // 유저가 이미 해당 포트폴리오를 조회했는지 확인
        List<String> viewedContentIds = redisDao.getValuesList(redisUserKey);
        if (viewedContentIds == null || !viewedContentIds.contains(portfolioIdx.toString())) {
            // 유저별 조회 이력에 포트폴리오 ID 저장
            redisDao.setValuesList(redisUserKey, portfolioIdx.toString());
            // 전체 조회수 증가
            redisDao.increment(totalKey);
            // 실시간 조회수 증가 (슬라이딩 윈도우 방식)
            redisDao.incrementRealtimeViewCount(portfolioIdx);
        }
    }

    // 실시간 인기 포트폴리오 조회수 슬라이딩 윈도우 관리 (2시간 범위 유지)
    @Scheduled(fixedRate = 60000) // 1분마다 슬라이딩 윈도우 적용
    public void applySlidingWindowForRealtimeViews() {
        redisDao.removeOldRealtimeData(2); // 2시간 이전의 데이터는 자동 삭제
    }

    // 전체 인기 포트폴리오 조회수 동기화 (1분마다 실행)
    @Scheduled(fixedRate = 60000)
    public void syncViewCountToDatabase() {
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        for (Portfolio portfolio : portfolioList) {
            String totalKey = "total:viewCount:" + portfolio.getIdx();
            String viewCountStr = redisDao.getValues(totalKey);

            if (viewCountStr != null) {
                int viewCnt = Integer.parseInt(viewCountStr);
                portfolio.setViewCnt(viewCnt);
                portfolioRepository.save(portfolio);
                redisDao.deleteValues(totalKey); // Redis 데이터 삭제
            }
        }
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

    /*포트폴리오 badge 부여*/
    @Transactional
    public void badgeToPortfolio(Portfolio portfolio){
        int viewCnt = portfolio.getViewCnt();
        int bookmarkCnt = portfolio.getBookmarkList().size();
        //포트폴리오 생성 기간 계산
        long portfolioSinceCreated = Duration.between(portfolio.getCreatedAt(), LocalDateTime.now()).toDays();

        //조회수에 따른 뱃지 부여
        if (viewCnt > 1000){
            assignBadge(portfolio, 1);
        }
        // 북마크 수에 따른 뱃지 부여
        if (bookmarkCnt > 100){
            assignBadge(portfolio, 2);
        }
        //TODO : 추후 수익률 구현과 함께 복합적으로 적용하도록 설정
        if(portfolioSinceCreated > 30) {
            assignBadge(portfolio, 3);
        }
    }

    public void assignBadge(Portfolio portfolio, long badgeIdx) {
        Badge badge = badgeRepository.findById(badgeIdx).orElseThrow();

        // 이미 같은 Badge가 있으면 중복 방지
        boolean alreadyHasBadge = rewardRepository.existsByPortfolioAndBadge(portfolio, badge);
        if (alreadyHasBadge) {
            return;
        }

        Reward reward = Reward.builder()
                .portfolio(portfolio)
                .badge(badge)
                .build();

        rewardRepository.save(reward);
    }
}