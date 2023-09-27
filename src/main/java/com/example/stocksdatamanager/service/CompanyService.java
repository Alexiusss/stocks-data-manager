package com.example.stocksdatamanager.service;

import com.example.stocksdatamanager.model.Company;
import com.example.stocksdatamanager.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.example.stocksdatamanager.util.JsonUtil.readValues;

@Service
public class CompanyService {

    @Value("${base.url}")
    String baseURL;

    @Value("${api.token}")
    String apiToken;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CompanyRepository companyRepository;

    public void requestCompaniesData() {

        ResponseEntity<String> response = restTemplate.getForEntity(baseURL + "ref-data/symbols?token=" + apiToken, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            List<Company> companies = readValues(response.getBody(), Company.class);
            companyRepository.saveAll(companies.subList(0, 200));
        }
    }
}