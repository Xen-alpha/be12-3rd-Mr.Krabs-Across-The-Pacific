package com.example.atp_back.stock;

import com.example.atp_back.common.BaseResponse;
import com.example.atp_back.stock.model.Stock;
import com.example.atp_back.stock.model.StockReply;
import com.example.atp_back.stock.model.req.StockReplyRegisterReq;
import com.example.atp_back.stock.model.resp.StockDetailResp;
import com.example.atp_back.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {
    private final StockService stockService;
    private final StockReplyService stockReplyService;

    @GetMapping("/detail/{idx}")
    public ResponseEntity<BaseResponse<StockDetailResp>> getStock(@PathVariable long idx) {
        BaseResponse<StockDetailResp> resp = new BaseResponse<>();
        resp.success(stockService.getStock(idx));
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/list")
    public ResponseEntity<BaseResponse<List<StockDetailResp>>> getStocks() {
        BaseResponse<List<StockDetailResp>> resp = new BaseResponse<>();
        resp.success(stockService.getAllStocks());
        return ResponseEntity.ok(resp);
    }



    @PostMapping("/reply/{stockId}")
    public ResponseEntity<BaseResponse<String>> getStockReply(@RequestBody StockReplyRegisterReq dto,
                                                              @AuthenticationPrincipal User user,
                                                              @PathVariable long stockId) {
        //login기능 구현하면 그때 입력하는걸로 일단은 1L
        return ResponseEntity.ok(stockReplyService.addReply(dto, user.getIdx(), stockId));
    }

}
