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

    public List<Company> findAll() {
        return crudCompanyRepository.findAll();
    }

    public void saveAll(List<Company> companies) {
        String values = companies.stream().map(this::renderSqlForCompany).collect(joining(","));
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

    private String renderSqlForCompany(Company company) {
        return "('" + company.getSymbol() + "','" +
                company.getName() + "'," +
                company.isEnabled() + ")";
    }
}