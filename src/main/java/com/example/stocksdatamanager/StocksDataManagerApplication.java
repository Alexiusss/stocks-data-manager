package com.example.stocksdatamanager;

import com.example.stocksdatamanager.service.CompanyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class StocksDataManagerApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(StocksDataManagerApplication.class, args);
        CompanyService service = context.getBean(CompanyService.class);
        service.requestCompaniesData();
    }
}