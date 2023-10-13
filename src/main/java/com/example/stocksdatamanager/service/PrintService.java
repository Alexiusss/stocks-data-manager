package com.example.stocksdatamanager.service;

import com.example.stocksdatamanager.model.Stock;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

import static com.example.stocksdatamanager.service.StockService.calculateStockValue;

@Service
public class PrintService {
    public void printMostExpensiveStocks(List<Stock> stocks) {
        printTableHeader("Most expensive stocks","Company name", "The value of a stock");

        stocks.forEach(stock -> {
            printTableRow(stock.getCompanyName(),
                    calculateStockValue(stock));
        });
        printSeparateLine();
    }

    public void printMostVolatileStocks(List<Stock> stocks) {
        printTableHeader("Most volatile stocks","Company name", "Latest price", "Change (%)");

        stocks.forEach(stock -> {
            printTableRow(stock.getCompanyName(),
                    String.valueOf(stock.getLatestPrice()),
                    String.valueOf(stock.getChange()));
        });
        printSeparateLine();
    }

    private void printTableHeader(String header, String... columnNames) {
        printSeparateLine();
        System.out.println(" ".repeat(77) + header.toUpperCase(Locale.ROOT));
        printSeparateLine();
        StringBuilder builder = new StringBuilder();

        List.of(columnNames).forEach(fieldName -> {
            builder.append(fieldName);
            builder.append(" ".repeat(80 - fieldName.length()));
        });
        System.out.println(builder);
        printSeparateLine();
    }

    private void printTableRow(String... values) {
        StringBuilder builder = new StringBuilder();
        List.of(values).forEach(value -> {
            builder.append(value);
            builder.append(" ".repeat(80 - value.length()));
        });
        System.out.println(builder);
    }

    private void printSeparateLine(){
        System.out.println("-".repeat(170));
    }
}
