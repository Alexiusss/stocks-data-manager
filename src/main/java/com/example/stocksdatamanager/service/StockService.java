package com.example.stocksdatamanager.service;

import com.example.stocksdatamanager.model.Stock;
import com.example.stocksdatamanager.repository.stock.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.example.stocksdatamanager.util.JsonUtil.readValue;

@Service
public class StockService {
    @Value("${base.url}")
    String baseURL;

    @Value("${api.token}")
    String apiToken;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockRepository stockRepository;

    public Stock requestStockData(String symbol) {
        ResponseEntity<String> response = restTemplate.getForEntity(baseURL + "stock/" + symbol + "/quote?token=" + apiToken, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return readValue(response.getBody(), Stock.class);
        }
        return new Stock();
    }

    @Transactional
    public void saveAll(List<Stock> stocks) {
        stockRepository.saveAll(stocks);
    }
}