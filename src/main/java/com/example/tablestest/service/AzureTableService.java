package com.example.tablestest.service;

import com.example.tablestest.PrintHelper;
import com.example.tablestest.repository.AzureTableRepository;
import com.example.tablestest.values.SensorEntity;
import com.microsoft.azure.storage.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AzureTableService {
    private AzureTableRepository repository;

    @Autowired
    public AzureTableService(AzureTableRepository repository) {
        this.repository = repository;
    }

    public void addSensorInfo(SensorEntity entity) {
        try{
            this.repository.batchInsertOfSensorEntities(entity);
        } catch (Throwable t) {
            PrintHelper.PrintException(t);
        }
    }

}
