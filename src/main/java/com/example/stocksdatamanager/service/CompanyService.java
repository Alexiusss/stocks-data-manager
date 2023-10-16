package com.example.stocksdatamanager.service;

import com.example.stocksdatamanager.model.Company;
import com.example.stocksdatamanager.repository.company.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.stocksdatamanager.util.JsonUtil.readValues;

@Service
@Slf4j
public class CompanyService extends BaseService{

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> requestCompaniesData() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseURL + "ref-data/symbols?token=" + apiToken, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            List<Company> companies = readValues(response.getBody(), Company.class);
            log.info("Successfully received data on " + companies.size() +" companies");
            return companies;
        } else {
            log.error("Status code: " + response.getStatusCode() + ", Error: " + response.getBody());
            return List.of();
        }
    }

    public List<Company> getAllActive() {
        log.info("Get all active companies");
        return companyRepository.findAllActive();
    }

    @Transactional
    public void saveAll(List<Company> companies) {
        companyRepository.saveAll(companies);
        log.info("Saved " + companies.size() + " companies");
    }

    public List<String> getSymbolsListForActiveCompanies(List<Company> companies) {
        return companies.stream()
                .parallel()
                .filter(Company::isEnabled)
                .map(Company::getSymbol)
                .collect(Collectors.toList());
    }
}