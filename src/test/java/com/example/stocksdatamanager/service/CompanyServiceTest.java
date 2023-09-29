package com.example.stocksdatamanager.service;

import com.example.stocksdatamanager.model.Company;
import com.example.stocksdatamanager.repository.company.CompanyRepository;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.example.stocksdatamanager.util.CompanyTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@Transactional
@AutoConfigureDataJpa
@ActiveProfiles("test")
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeAll
    static void init() {
        WireMockServer wireMockServer = new WireMockServer(new WireMockConfiguration().port(7070));
        wireMockServer.start();
        WireMock.configureFor("localhost", 7070);
    }

    @Test
    public void requestCompaniesData() {
        stubExternalAPIResponse();

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