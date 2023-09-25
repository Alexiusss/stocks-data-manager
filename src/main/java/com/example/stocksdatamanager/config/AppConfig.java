package com.example.stocksdatamanager.config;

import com.example.stocksdatamanager.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Autowired
    private void setMapper(ObjectMapper objectMapper){
        JsonUtil.setMapper(objectMapper);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}