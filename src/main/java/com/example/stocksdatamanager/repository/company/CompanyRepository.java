package com.example.stocksdatamanager.repository.company;

import com.example.stocksdatamanager.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class CompanyRepository {

    @Autowired
    CrudCompanyRepository crudCompanyRepository;

    public List<Company> findAll() {
        return crudCompanyRepository.findAll();
    }

    public void saveAll(List<Company> companies) {
        crudCompanyRepository.saveAll(companies);
    }
}