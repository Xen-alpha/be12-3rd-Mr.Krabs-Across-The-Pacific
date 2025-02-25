package com.example.atp_back.stock;

import com.example.atp_back.common.BaseResponse;
import com.example.atp_back.stock.model.req.StockGraphReq;
import com.example.atp_back.stock.model.resp.StockGraphResp;
import com.example.atp_back.stock.service.StockGraphService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stockgraph")
public class StockGraphController {
    private final StockGraphService graphService;

    @GetMapping("/list")
    public ResponseEntity<BaseResponse<List<StockGraphResp>>> getStockGraphList(@RequestBody StockGraphReq codes) {
            BaseResponse<List<StockGraphResp>> response = BaseResponse.<List<StockGraphResp>>builder().isSuccess(true).result(graphService.getGraphList(codes.getCodes())).build();
            return ResponseEntity.ok(response);
    }
}
