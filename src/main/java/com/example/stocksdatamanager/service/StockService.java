package com.example.stocksdatamanager.service;

import com.example.stocksdatamanager.model.Company;
import com.example.stocksdatamanager.model.Stock;
import com.example.stocksdatamanager.repository.stock.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static com.example.stocksdatamanager.util.JsonUtil.readValue;

@Slf4j
@Service
public class StockService extends BaseService{

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    CompanyService companyService;

    // https://sysout.ru/completablefuture/
    public List<Stock> requestStocksData() throws ExecutionException, InterruptedException {
        List<Company> activeCompanies = Collections.synchronizedList(companyService.getAllActive());
        log.info("Request stock data for " + activeCompanies.size() + " companies");
        List<CompletableFuture<Stock>> pageStockFutures = activeCompanies.stream()
                .map(this::requestStockData)
                .collect(Collectors.toList());

        return CompletableFuture.allOf(pageStockFutures.toArray(new CompletableFuture[0]))
                .thenApply(v -> pageStockFutures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()))
                .get();
    }

    public CompletableFuture<Stock> requestStockData(Company company) {
        return CompletableFuture.supplyAsync(() -> {
                    ResponseEntity<String> response = restTemplate.getForEntity(baseURL + "stock/" + company.getSymbol() + "/quote?token=" + apiToken, String.class);
                    if (response.getStatusCode().is2xxSuccessful()) {
                        Stock stock = readValue(response.getBody(), Stock.class);
                        stock.setCompanyId(company.getId());
                        return stock;
                    } else {
                        log.error("Status code: " + response.getStatusCode() + ", Error: " + response.getBody());
                        return new Stock();
                    }

                }
        );
    }

    @Transactional
    public void saveAll(List<Stock> stocks) {
        stockRepository.saveAll(stocks);
        log.info("Saved " + stocks.size() + " stocks");

    }

    public List<Stock> getMostExpensiveStocks() {
        log.info("Get info about the most expensive stocks");
        return stockRepository.findMostExpensiveStocks();
    }

    public List<Stock> getMostVolatileStocks() {
        log.info("Get info about the most volatile stocks");
        return stockRepository.findMostVolatileStocks();
    }

    public static String calculateStockValue(Stock stock) {
        int volume = stock.getVolume() != null ? stock.getVolume() : stock.getPreviousVolume();

        double value = stock.getLatestPrice() * volume;

        return new DecimalFormat("#,###.00").format(value);
    }
}