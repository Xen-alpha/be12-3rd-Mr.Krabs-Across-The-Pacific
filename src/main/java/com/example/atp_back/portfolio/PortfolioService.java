package com.example.atp_back.portfolio;

import com.example.atp_back.common.RedisDao;
import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.portfolio.model.request.PortfolioCreateReqDto;
import com.example.atp_back.portfolio.model.response.PortfolioInstanceResp;
import com.example.atp_back.portfolio.model.response.PortfolioListResp;
import com.example.atp_back.portfolio.model.response.PortfolioPageResp;
import com.example.atp_back.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final RedisDao redisDao;

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

    public PortfolioInstanceResp read(@Nullable User user, Long portfolioIdx) {
        Portfolio portfolio = portfolioRepository.findById(portfolioIdx).orElseThrow();
        return PortfolioInstanceResp.from(user, portfolio);
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


    /*포트폴리오 조회수 관련*/
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
}