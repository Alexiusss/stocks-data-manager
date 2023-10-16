package com.example.stocksdatamanager.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@Transactional
@AutoConfigureDataJpa
@ActiveProfiles("test")
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
public abstract class AbstractServiceTest {

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void init() {
        wireMockServer = new WireMockServer(new WireMockConfiguration().port(7070));
        wireMockServer.start();
        WireMock.configureFor("localhost", 7070);
    }

    @AfterAll
    static void destroy() {
        wireMockServer.stop();
    }
}