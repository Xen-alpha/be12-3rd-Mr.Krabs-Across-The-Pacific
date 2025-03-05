package com.example.atp_back.portfolio.model.request;

import com.example.atp_back.portfolio.model.entity.Bookmark;
import com.example.atp_back.portfolio.model.entity.Portfolio;
import com.example.atp_back.user.model.User;
import lombok.Getter;


@Getter
public class PortfolioBookmarkReq {
    private Long portfolioIdx;
    private boolean bookmark;
}