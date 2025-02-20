package com.example.atp_back.stock;

import com.example.atp_back.common.BaseResponse;
import com.example.atp_back.stock.model.resp.StockDetailResp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {
    private final StockService stockService;

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
}
