package com.example.tablestest.service;

import com.example.tablestest.repository.WioRepository;
import com.example.tablestest.values.SensorEntity;
import com.example.tablestest.values.TemperatureData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

// RepositoryからJsonをマッピングしたクラスを取得し、AzureTableへ格納できる形へと変換するService
@Service
public class WioService {
    private static final Logger logger = LoggerFactory.getLogger(WioService.class);

    private WioRepository repository;

    @Autowired
    public WioService(WioRepository repository) {
        this.repository = repository;
    }

    public SensorEntity getSensorInfo() {
        try{
            // 現在時刻の取得
            String ldt = LocalDateTime.now().toString();

            //温度センサー情報の取得
            TemperatureData data = repository.getTemperature();

            //AzureTableStorageに格納できる形に変換
            SensorEntity entity = new SensorEntity("Temperature", ldt);
            entity.setValue(data.getTemperature());
            return entity;
        } catch ( HttpClientErrorException e ) {
            logger.warn("Failed to get a sensor data.");
            return null;
        }
    }
}
