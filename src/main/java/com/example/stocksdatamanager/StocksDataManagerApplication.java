package com.example.stocksdatamanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class StocksDataManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StocksDataManagerApplication.class, args);
    }
}