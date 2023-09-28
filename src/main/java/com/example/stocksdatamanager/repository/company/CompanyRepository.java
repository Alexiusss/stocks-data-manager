package com.example.stocksdatamanager.repository.company;

import com.example.stocksdatamanager.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Repository
public class CompanyRepository {

    @Autowired
    CrudCompanyRepository crudCompanyRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public void saveAll(List<Company> objects) {
        String values = objects.stream().map(this::renderSqlForObj).collect(joining(","));
        String saveSQL = "INSERT INTO companies (symbol, name, is_enabled) VALUES "
                + values +
                "ON CONFLICT (symbol) " +
                "DO UPDATE SET " +
                "name = EXCLUDED.name, " +
                "is_enabled = EXCLUDED.is_enabled;";
        entityManager.createNativeQuery(saveSQL).executeUpdate();
        entityManager.flush();
        entityManager.clear();
    }

    private String renderSqlForObj(Company company) {
        return "('" + company.getSymbol() + "','" +
                company.getName() + "'," +
                company.isEnabled() + ")";
    }
}