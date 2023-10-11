package com.example.stocksdatamanager.service;

import com.example.stocksdatamanager.model.Stock;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrintService {
    public void printMostExpensiveStocks(List<Stock> stocks) {
        printTableHeader("Company name", "The value of a stock");

        stocks.forEach(stock -> {
            printTableRow(stock.getCompanyName(),
                    calculateValue(stock));
        });
        printSeparateLine();
    }

    public void printMostVolatileStocks(List<Stock> stocks) {
        printTableHeader("Company name", "Latest price", "Change");

        stocks.forEach(stock -> {
            printTableRow(stock.getCompanyName(),
                    String.valueOf(stock.getLatestPrice()),
                    String.valueOf(stock.getChange()));
        });
        printSeparateLine();
    }

    private void printTableHeader(String... columnNames) {
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

    private String calculateValue(Stock stock) {
        int value = (int)stock.getLatestPrice() * stock.getPreviousVolume();
        return String.valueOf(value);
    }
}
