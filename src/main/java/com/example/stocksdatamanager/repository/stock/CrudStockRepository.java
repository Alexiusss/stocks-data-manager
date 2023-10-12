package com.example.stocksdatamanager.repository.stock;

import com.example.stocksdatamanager.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CrudStockRepository extends JpaRepository<Stock, String> {
    @Query(value = "SELECT * FROM stocks " +
            "ORDER BY CASE " +
            "WHEN volume IS NOT NULL THEN (volume * latest_price) " +
            "ELSE (previous_volume * latest_price) " +
            "END DESC " +
            "LIMIT 5",
            nativeQuery = true)
    List<Stock> findMostExpensiveStocks();

    @Query(value = "SELECT * FROM stocks " +
            "ORDER BY change DESC " +
            "LIMIT 5",
            nativeQuery = true)
    List<Stock> findMostVolatileStocks();
}
