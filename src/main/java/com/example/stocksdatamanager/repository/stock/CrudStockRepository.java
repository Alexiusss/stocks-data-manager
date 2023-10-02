package com.example.stocksdatamanager.repository.stock;

import com.example.stocksdatamanager.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudStockRepository extends JpaRepository<Stock, String> {
}
