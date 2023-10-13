package com.example.stocksdatamanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Transactional(readOnly = true)
public class BaseService {

    @Value("${base.url}")
    String baseURL;

    @Value("${api.token}")
    String apiToken;

    @Autowired
    protected RestTemplate restTemplate;

}
