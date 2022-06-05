package com.example.tablestest.service;

import com.example.tablestest.repository.WioRepository;
import com.example.tablestest.values.SensorEntity;
import com.example.tablestest.values.TemperatureData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

// RepositoryからJsonをマッピングしたクラスを取得し、AzureTableへ格納できる形へと変換するService
@Service
public class WioService {

    private WioRepository repository;

    @Autowired
    public WioService(WioRepository repository) {
        this.repository = repository;
    }

    public SensorEntity getSensorInfo() {
        // 現在時刻の取得
        String ldt = LocalDateTime.now().toString();

        //温度センサー情報の取得
        TemperatureData data = repository.getTemperature();

        //AzureTableStorageに格納できる形に変換
        SensorEntity entity = new SensorEntity("Temperature", ldt);
        entity.setValue(data.getTemperature());
        return entity;
    }
}
