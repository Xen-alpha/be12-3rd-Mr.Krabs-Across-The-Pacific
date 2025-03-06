package com.example.atp_back.stock.service;

import com.example.atp_back.common.code.status.ErrorStatus;
import com.example.atp_back.common.exception.handler.StockHandler;
import com.example.atp_back.stock.model.StockReply;
import com.example.atp_back.stock.model.StockReplyLikes;
import com.example.atp_back.stock.repository.StockReplyLikesRepository;
import com.example.atp_back.stock.repository.StockReplyRepository;
import com.example.atp_back.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockReplyLikesService {
    private final StockReplyLikesRepository stockReplyLikesRepository;
    private final StockReplyRepository stockReplyRepository;

    @Transactional
    public void likeReply(User user, Long replyId) {

        StockReply reply = stockReplyRepository.findById(replyId).orElseThrow(() -> new StockHandler(ErrorStatus.REPLY_NOT_FOUND));

        try {
            stockReplyLikesRepository.save(StockReplyLikes.builder()
                    .user(user)
                    .reply(reply)
                    .build());
        }
        catch (Exception e) {
            throw new StockHandler(ErrorStatus.REPLY_LIKES_FAILED);
        }

        try {
            stockReplyRepository.save(reply);
            reply.addLikesCount();
        }
        catch (Exception e) {
            throw new StockHandler(ErrorStatus.REPLY_OPTIMISTIC_LOCK_FAILED);
        }
    }
}
