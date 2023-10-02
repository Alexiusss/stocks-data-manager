package com.example.stocksdatamanager.repository.stock;

import com.example.stocksdatamanager.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Repository
public class StockRepository {

    @Autowired
    CrudStockRepository crudStockRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Stock> findAll() {
        return crudStockRepository.findAll();
    }

    public void saveAll(List<Stock> stocks) {
        String values = stocks.stream().map(this::renderSqlForStock).collect(joining(","));
        String saveSQL = "INSERT INTO stocks (symbol, latest_price, change, company_name, previous_volume, volume) VALUES "
                + values +
                "ON CONFLICT (symbol) " +
                "DO UPDATE SET " +
                "name = EXCLUDED.name, " +
                "latest_price  = EXCLUDED.latest_price, " +
                "company_name = EXCLUDED.company_name, " +
                "previous_volume = EXCLUDED.previous_volume, " +
                "volume = EXCLUDED.volume;";
        entityManager.createNativeQuery(saveSQL).executeUpdate();
        entityManager.flush();
        entityManager.clear();
    }

    private String renderSqlForStock(Stock stock) {
        return "('" + stock.getSymbol() + "','" +
                stock.getLatestPrice() + "'," +
                stock.getChange() + "'," +
                stock.getCompanyName() + "'," +
                stock.getPreviousVolume() + "'," +
                stock.getVolume() + ")";
    }
}