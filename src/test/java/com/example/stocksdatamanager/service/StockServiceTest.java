package com.example.stocksdatamanager.service;

import com.example.stocksdatamanager.model.Stock;
import com.example.stocksdatamanager.repository.stock.StockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.example.stocksdatamanager.util.StockTestData.*;
import static org.assertj.core.api.Assertions.assertThat;


public class StockServiceTest extends CommonServiceTest {

    @Autowired
    StockService stockService;

    @Autowired
    StockRepository stockRepository;

    @Test
    public void requestStockData() {
        stubStockAPIResponse("MSFT");

        Stock stock = stockService.requestStockData("MSFT");

        assertThat(stock.getSymbol()).isEqualTo("MSFT");
    }

    @Test
    public void saveAll() {
        stockService.saveAll(STOCK_LIST);

        assertThat(stockRepository.findAll()).containsExactlyInAnyOrderElementsOf(STOCK_LIST);
    }
}