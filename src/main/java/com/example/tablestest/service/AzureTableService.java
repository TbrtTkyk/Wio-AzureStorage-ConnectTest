package com.example.tablestest.service;

import com.example.tablestest.PrintHelper;
import com.example.tablestest.repository.AzureTableRepository;
import com.example.tablestest.values.SensorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AzureTableService {
    private static final Logger logger = LoggerFactory.getLogger(AzureTableService.class);

    private AzureTableRepository repository;

    @Autowired
    public AzureTableService(AzureTableRepository repository) {
        this.repository = repository;
    }

    // センサー情報（SensorEntity）をデータベースに追加する
    public void addSensorInfo(SensorEntity entity) throws Throwable{
        if ( entity == null ) return;
        try{
            this.repository.insertOfSensorEntity(entity);
        } catch (Throwable t) {
            logger.error("Failed to add a sensor entity.");
            PrintHelper.PrintException(t);
            throw t;
        }
    }
    public void addSensorInfo(List<SensorEntity> entities) throws Throwable{
        if ( entities == null ) return;
        try{
            this.repository.batchInsertOfSensorEntities(entities);
        } catch (Throwable t) {
            logger.error("Failed to add sensor entities.");
            PrintHelper.PrintException(t);
            throw t;
        }
    }

    // センサーのデータの内、古いものを削除してデータ量を調整する
    public void deleteOverSize() throws Throwable{
        try{
            this.repository.deleteOverSize("Temperature", 10);
        } catch (Throwable t) {
            logger.error("Failed to delete sensor entities.");
            PrintHelper.PrintException(t);
            throw t;
        }
    }

    // 最新のセンサー情報を取得する
    public SensorEntity getMostPrevious() {
        try{
            return this.repository.getMostPrevious("Temperature");
        } catch (Throwable t) {
            logger.warn("Failed to get most previous one.");
            PrintHelper.PrintException(t);
            return null;
        }
    }

}
