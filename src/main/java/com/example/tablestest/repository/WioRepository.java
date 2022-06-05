package com.example.tablestest.repository;

import com.example.tablestest.values.HelloWorld;
import com.example.tablestest.values.TemperatureData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

// WioからJsonデータをマッピングして取得するクラス
@Repository
public class WioRepository {

    private final String url = "http://localhost:5000/hello";

    private RestTemplate restTemplate;

    @Autowired
    public WioRepository(RestTemplateBuilder templateBuilder) {
        this.restTemplate = templateBuilder.build();
    }

    public TemperatureData getTemperature() {
        TemperatureData response = restTemplate.getForObject(
                url, TemperatureData.class);
        return response;
    }

    public HelloWorld getHelloWorld() {
        HelloWorld response = restTemplate.getForObject(
                url, HelloWorld.class);
        return response;
    }
}
