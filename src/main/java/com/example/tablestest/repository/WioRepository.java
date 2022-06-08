package com.example.tablestest.repository;

import com.example.tablestest.component.WioURLProvider;
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
    private WioURLProvider URLProvider;

    @Autowired
    public WioRepository(RestTemplateBuilder templateBuilder, WioURLProvider urlProvider) {
        this.restTemplate = templateBuilder.build();
        this.URLProvider = urlProvider;
    }

    public TemperatureData getTemperature() throws HttpClientErrorException {
        TemperatureData response = restTemplate.getForObject(
                URLProvider.getURLForTemperature(), TemperatureData.class);
        return response;
    }
}
