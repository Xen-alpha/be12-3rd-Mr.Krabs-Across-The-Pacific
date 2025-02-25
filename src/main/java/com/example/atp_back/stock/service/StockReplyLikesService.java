package com.example.atp_back.stock.service;

import com.example.atp_back.stock.model.StockReply;
import com.example.atp_back.stock.model.StockReplyLikes;
import com.example.atp_back.stock.repository.StockReplyLikesRepository;
import com.example.atp_back.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockReplyLikesService {
    private final StockReplyLikesRepository stockReplyLikesRepository;

    public void likeReply(User user, Long replyId) {
        StockReply reply = StockReply.builder()
                .idx(replyId)
                .build();

        stockReplyLikesRepository.save(StockReplyLikes.builder()
                .user(user)
                .reply(reply)
                .build());
    }
}
