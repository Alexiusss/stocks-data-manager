package com.example.stocksdatamanager.service;

import com.example.stocksdatamanager.model.Company;
import com.example.stocksdatamanager.repository.company.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.stocksdatamanager.util.JsonUtil.readValues;

@Service
@Transactional(readOnly = true)
public class CompanyService {

    @Value("${base.url}")
    String baseURL;

    @Value("${api.token}")
    String apiToken;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> requestCompaniesData() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseURL + "ref-data/symbols?token=" + apiToken, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return readValues(response.getBody(), Company.class);
        }
        return List.of();
    }

    public List<Company> getAllActive() {
        return companyRepository.findAllActive();
    }

    @Transactional
    public void saveAll(List<Company> companies) {
        companyRepository.saveAll(companies);
    }

    public List<String> getSymbolsListForActiveCompanies(List<Company> companies) {
        return companies.stream()
                .filter(Company::isEnabled)
                .map(Company::getSymbol)
                .collect(Collectors.toList());
    }
}