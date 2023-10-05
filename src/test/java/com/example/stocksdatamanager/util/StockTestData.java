package com.example.stocksdatamanager.util;

import com.example.stocksdatamanager.model.Stock;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.experimental.UtilityClass;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@UtilityClass
public class StockTestData {

    public static final Stock STOCK = new Stock(null, "MSFT", 77.55, 2.33, "Microsoft Corporation", 12345678, null);

    public static final List<Stock> STOCK_LIST = List.of(
            STOCK,
            new Stock(null, "A", 111.05, 0.77, "Agilent Technologies Inc.", 21436525, null),
            new Stock(null, "AA", 77.12, 0.55, "Alcoa Corp", 65456411, null),
            new Stock(null, "AAA", 89.37, 1.27, "Investment Managers Series Trust II - AXS First Priority CLO Bond ETF", 23654564, null)
    );

    private final String apiResponse = "{\n" +
            "  \"avgTotalVolume\": 19712208,\n" +
            "  \"calculationPrice\": \"tops\",\n" +
            "  \"change\": 1.85,\n" +
            "  \"changePercent\": 0.00577,\n" +
            "  \"close\": null,\n" +
            "  \"closeSource\": \"official\",\n" +
            "  \"closeTime\": null,\n" +
            "  \"companyName\": \"Microsoft Corporation\",\n" +
            "  \"currency\": \"USD\",\n" +
            "  \"delayedPrice\": null,\n" +
            "  \"delayedPriceTime\": null,\n" +
            "  \"extendedChange\": null,\n" +
            "  \"extendedChangePercent\": null,\n" +
            "  \"extendedPrice\": null,\n" +
            "  \"extendedPriceTime\": null,\n" +
            "  \"high\": null,\n" +
            "  \"highSource\": \"IEX real time price\",\n" +
            "  \"highTime\": 1695307379082,\n" +
            "  \"iexAskPrice\": 342,\n" +
            "  \"iexAskSize\": 200,\n" +
            "  \"iexBidPrice\": 322.6,\n" +
            "  \"iexBidSize\": 100,\n" +
            "  \"iexClose\": 322.62,\n" +
            "  \"iexCloseTime\": 1695307837235,\n" +
            "  \"iexLastUpdated\": 1695307837235,\n" +
            "  \"iexMarketPercent\": 0.0254960105021448,\n" +
            "  \"iexOpen\": 319.26,\n" +
            "  \"iexOpenTime\": 1695303000126,\n" +
            "  \"iexRealtimePrice\": 322.62,\n" +
            "  \"iexRealtimeSize\": 120,\n" +
            "  \"iexVolume\": 329448,\n" +
            "  \"lastTradeTime\": 1695307837235,\n" +
            "  \"latestPrice\": 322.62,\n" +
            "  \"latestSource\": \"IEX real time price\",\n" +
            "  \"latestTime\": \"10:50:37 AM\",\n" +
            "  \"latestUpdate\": 1695307837235,\n" +
            "  \"latestVolume\": null,\n" +
            "  \"low\": null,\n" +
            "  \"lowSource\": null,\n" +
            "  \"lowTime\": null,\n" +
            "  \"marketCap\": 2396990461680,\n" +
            "  \"oddLotDelayedPrice\": null,\n" +
            "  \"oddLotDelayedPriceTime\": null,\n" +
            "  \"open\": null,\n" +
            "  \"openTime\": null,\n" +
            "  \"openSource\": \"official\",\n" +
            "  \"peRatio\": 33.33,\n" +
            "  \"previousClose\": 320.77,\n" +
            "  \"previousVolume\": 21436525,\n" +
            "  \"primaryExchange\": \"NASDAQ\",\n" +
            "  \"symbol\": \"MSFT\",\n" +
            "  \"volume\": null,\n" +
            "  \"week52High\": 366.01,\n" +
            "  \"week52Low\": 211.39,\n" +
            "  \"ytdChange\": 0.352441441472039,\n" +
            "  \"isUSMarketOpen\": true\n" +
            "}";

    public static void stubStockAPIResponse(String symbol) {
        stubFor(WireMock.get(urlMatching("/stock/" + symbol + "/quote\\?token=test_api_token"))
                .willReturn(ok(apiResponse)));
    }
}