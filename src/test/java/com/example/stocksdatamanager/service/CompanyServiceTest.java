package com.example.stocksdatamanager.service;

import com.example.stocksdatamanager.model.Company;
import com.example.stocksdatamanager.repository.company.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.stocksdatamanager.util.CompanyTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

class CompanyServiceTest extends CommonServiceTest{

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void requestCompaniesData() {
        stubCompanyAPIResponse();

        List<Company> companies = companyService.requestCompaniesData();

        assertThat(companies).containsExactlyInAnyOrderElementsOf(COMPANIES_LIST);
    }

    @Test
    public void saveAll() {
        companyService.saveAll(COMPANIES_LIST);

        assertThat(companyRepository.findAll()).containsExactlyInAnyOrderElementsOf(COMPANIES_LIST);
    }

    @Test
    public void getSymbolsListForActiveCompanies() {
        List<String> symbolsListForActiveCompanies = companyService.getSymbolsListForActiveCompanies(COMPANIES_LIST);

        assertThat(symbolsListForActiveCompanies).containsExactlyInAnyOrderElementsOf(SYMBOLS_LIST);
    }
}