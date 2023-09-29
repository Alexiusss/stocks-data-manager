package com.example.stocksdatamanager.util;

import com.example.stocksdatamanager.model.Company;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.experimental.UtilityClass;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@UtilityClass
public class CompanyTestData {

    public static final List<Company> COMPANIES_LIST = List.of(
            new Company("A","Agilent Technologies Inc.", true),
            new Company("AA","Alcoa Corp", true),
            new Company("AAA","Investment Managers Series Trust II - AXS First Priority CLO Bond ETF", true)
    );

    public static final List<String> SYMBOLS_LIST = List.of("A", "AA", "AAA");

    private final String apiResponse = "[\n" +
            "  {\n" +
            "    \"symbol\": \"A\",\n" +
            "    \"exchange\": \"XNYS\",\n" +
            "    \"exchangeSuffix\": \"\",\n" +
            "    \"exchangeName\": \"New York Stock Exchange Inc\",\n" +
            "    \"exchangeSegment\": \"XNYS\",\n" +
            "    \"exchangeSegmentName\": \"New York Stock Exchange Inc\",\n" +
            "    \"name\": \"Agilent Technologies Inc.\",\n" +
            "    \"date\": \"2023-09-21\",\n" +
            "    \"type\": \"cs\",\n" +
            "    \"iexId\": \"IEX_46574843354B2D52\",\n" +
            "    \"region\": \"US\",\n" +
            "    \"currency\": \"USD\",\n" +
            "    \"isEnabled\": true,\n" +
            "    \"figi\": \"BBG000C2V3D6\",\n" +
            "    \"cik\": \"0001090872\",\n" +
            "    \"lei\": \"QUIX8Y7A2WP0XRMW7G29\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"symbol\": \"AA\",\n" +
            "    \"exchange\": \"XNYS\",\n" +
            "    \"exchangeSuffix\": \"\",\n" +
            "    \"exchangeName\": \"New York Stock Exchange Inc\",\n" +
            "    \"exchangeSegment\": \"XNYS\",\n" +
            "    \"exchangeSegmentName\": \"New York Stock Exchange Inc\",\n" +
            "    \"name\": \"Alcoa Corp\",\n" +
            "    \"date\": \"2023-09-21\",\n" +
            "    \"type\": \"cs\",\n" +
            "    \"iexId\": \"IEX_4238333734532D52\",\n" +
            "    \"region\": \"US\",\n" +
            "    \"currency\": \"USD\",\n" +
            "    \"isEnabled\": true,\n" +
            "    \"figi\": \"BBG00B3T3HD3\",\n" +
            "    \"cik\": \"0001675149\",\n" +
            "    \"lei\": \"549300T12EZ1F6PWWU29\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"symbol\": \"AAA\",\n" +
            "    \"exchange\": \"ARCX\",\n" +
            "    \"exchangeSuffix\": \"\",\n" +
            "    \"exchangeName\": \"Nyse Arca\",\n" +
            "    \"exchangeSegment\": \"ARCX\",\n" +
            "    \"exchangeSegmentName\": \"Nyse Arca\",\n" +
            "    \"name\": \"Investment Managers Series Trust II - AXS First Priority CLO Bond ETF\",\n" +
            "    \"date\": \"2023-09-21\",\n" +
            "    \"type\": \"et\",\n" +
            "    \"iexId\": \"IEX_5030314338392D52\",\n" +
            "    \"region\": \"US\",\n" +
            "    \"currency\": \"USD\",\n" +
            "    \"isEnabled\": true,\n" +
            "    \"figi\": \"BBG01B0JRCS6\",\n" +
            "    \"cik\": \"0001587982\",\n" +
            "    \"lei\": null\n" +
            "  }\n" +
            "]  ";

    public static void stubExternalAPIResponse() {
        stubFor(WireMock.get(urlMatching( "/ref-data/symbols\\?token=test_api_token"))
                .willReturn(ok(apiResponse)));
    }
}