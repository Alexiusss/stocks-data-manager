package com.example.stocksdatamanager.service;

import com.example.stocksdatamanager.model.Company;
import com.example.stocksdatamanager.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class JobService {

    @Autowired
    CompanyService companyService;

    @Autowired
    StockService stockService;

    @Autowired
    PrintService printService;

    @Scheduled(fixedDelayString = "PT24H")
    public void testCompany() {
        List<Company> companies = companyService.requestCompaniesData().subList(0, 200);
        companyService.saveAll(companies);
    }

    @Scheduled(initialDelay = 2000, fixedDelay = 5000)
    public void testStocks() throws ExecutionException, InterruptedException {

        List<Stock> stocks = stockService.requestStocksData();

        stockService.saveAll(stocks);

        List<Stock> mostExpensiveStocks = stockService.getMostExpensiveStocks();
        printService.printMostExpensiveStocks(mostExpensiveStocks);

        List<Stock> mostVolatileStocks = stockService.getMostVolatileStocks();
        printService.printMostVolatileStocks(mostVolatileStocks);
    }
}