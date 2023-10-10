package com.example.stocksdatamanager.repository.stock;

import com.example.stocksdatamanager.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class StockRepository {

    @Autowired
    CrudStockRepository crudStockRepository;

    public List<Stock> findAll() {
        return crudStockRepository.findAll();
    }

    public List<Stock> findMostExpensiveStocks() {
        return crudStockRepository.findMostExpensiveStocks();
    }

    public List<Stock> findMostVolatileStocks(){
        return crudStockRepository.findMostVolatileStocks();
    }

    public void saveAll(List<Stock> stocks) {
        crudStockRepository.saveAll(stocks);
    }
}