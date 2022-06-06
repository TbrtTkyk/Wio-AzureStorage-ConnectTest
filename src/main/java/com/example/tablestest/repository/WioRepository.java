package com.example.tablestest.repository;

import com.example.tablestest.config.WioURLProvider;
import com.example.tablestest.values.TemperatureData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

// WioからJsonデータをマッピングして取得するクラス
@Repository
public class WioRepository {
    private RestTemplate restTemplate;

    @Autowired
    public WioRepository(RestTemplateBuilder templateBuilder) {
        this.restTemplate = templateBuilder.build();
    }

    public TemperatureData getTemperature() throws HttpClientErrorException {
        TemperatureData response = restTemplate.getForObject(
                WioURLProvider.temperature(), TemperatureData.class);
        return response;
    }
}
