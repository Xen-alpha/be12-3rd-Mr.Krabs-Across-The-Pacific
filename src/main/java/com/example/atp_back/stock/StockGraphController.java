package com.example.atp_back.stock;

import com.example.atp_back.common.BaseResponse;
import com.example.atp_back.stock.model.req.*;
import com.example.atp_back.stock.model.resp.*;
import com.example.atp_back.stock.service.StockGraphService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name="주가 변화 데이터 요청", description = "그래프 그리기에 필요한 주가 변화 데이터를 받는 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/stockgraph")
public class StockGraphController {
    private final StockGraphService graphService;

    @Operation(description="body로 요청한 종목 코드 리스트에 따라 2년치 주가 변화 데이터를 응답함")
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<List<StockGraphResp>>> getStockGraphList(@RequestBody StockGraphReq codes) {
            BaseResponse<List<StockGraphResp>> response = BaseResponse.<List<StockGraphResp>>builder().isSuccess(true).result(graphService.getGraphList(codes.getCodes())).build();
            return ResponseEntity.ok(response);
    }
    @Operation(description="body로 요청한 종목 코드 리스트에 따라 2년치 주가 변화 데이터를 응답함")
    @GetMapping("/one")
    public ResponseEntity<BaseResponse<StockGraphResp>> getSingleStockGraph(String code) {
            BaseResponse<StockGraphResp> response = BaseResponse.<StockGraphResp>builder()
                    .isSuccess(true).result(graphService.getGraph(code))
                    .build();
            return ResponseEntity.ok(response);
    }
}
