package com.example.stocksdatamanager.service;

import com.example.stocksdatamanager.model.Company;
import com.example.stocksdatamanager.model.Stock;
import com.example.stocksdatamanager.repository.stock.StockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutionException;

import static com.example.stocksdatamanager.util.StockTestData.STOCK_LIST;
import static com.example.stocksdatamanager.util.StockTestData.stubStockAPIResponse;
import static org.assertj.core.api.Assertions.assertThat;


public class StockServiceTest extends AbstractServiceTest {

    @Autowired
    StockService stockService;

    @Autowired
    StockRepository stockRepository;

    @Test
    public void requestStockData() throws ExecutionException, InterruptedException {
        stubStockAPIResponse("MSFT");

        Stock stock = stockService.requestStockData(new Company(null, "MSFT", "Microsoft", true)).get();

        assertThat(stock.getSymbol()).isEqualTo("MSFT");
    }

    @Test
    public void saveAll() {
        stockService.saveAll(STOCK_LIST);

        assertThat(stockRepository.findAll()).containsExactlyInAnyOrderElementsOf(STOCK_LIST);
    }
}