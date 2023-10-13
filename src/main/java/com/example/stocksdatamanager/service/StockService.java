package com.example.stocksdatamanager.service;

import com.example.stocksdatamanager.model.Company;
import com.example.stocksdatamanager.model.Stock;
import com.example.stocksdatamanager.repository.stock.StockRepository;
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

@Service
public class StockService extends BaseService{

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    CompanyService companyService;

    // https://sysout.ru/completablefuture/
    public List<Stock> requestStocksData() throws ExecutionException, InterruptedException {
        List<Company> activeCompanies = Collections.synchronizedList(companyService.getAllActive());

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
                    }
                    return new Stock();
                }
        );
    }

    @Transactional
    public void saveAll(List<Stock> stocks) {
        stockRepository.saveAll(stocks);
    }

    public List<Stock> getMostExpensiveStocks() {
        return stockRepository.findMostExpensiveStocks();
    }

    public List<Stock> getMostVolatileStocks() {
        return stockRepository.findMostVolatileStocks();
    }

    public static String calculateStockValue(Stock stock) {
        int volume = stock.getVolume() != null ? stock.getVolume() : stock.getPreviousVolume();

        double value = stock.getLatestPrice() * volume;

        return new DecimalFormat("#,###.00").format(value);
    }
}