package com.example.tablestest.repository;

import com.example.tablestest.values.SensorEntity;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableBatchOperation;
import com.microsoft.azure.storage.table.TableQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;

@Repository
public class AzureTableRepository {
    protected static CloudTableClient tableClient = null;
    protected static CloudTable table = null;

    @Autowired
    public AzureTableRepository() throws RuntimeException, URISyntaxException, InvalidKeyException, StorageException {
        // Tableサービスに干渉するためのTableClientを作成
        tableClient = TableClientProvider.getTableClientReference();
        // SensorsTestという名前のテーブルを取得
        table = tableClient.getTableReference("SensorsTest");
    }

    // SensorEntityをまとめて挿入・更新する
    public void batchInsertOfSensorEntities(ArrayList<SensorEntity> entities) throws StorageException{
        TableBatchOperation batchOperation = new TableBatchOperation();

        for (SensorEntity entity : entities) {
            batchOperation.insertOrMerge(entity);
        }

        table.execute(batchOperation);
    }
    // SensorEntityを挿入・更新する
    public void batchInsertOfSensorEntities(SensorEntity entity) throws StorageException{
        TableBatchOperation batchOperation = new TableBatchOperation();
        batchOperation.insertOrMerge(entity);
        table.execute(batchOperation);
    }

    // query関係（製作途中）
    public void querySensorEntity(String sensorName, String endRowKey) {
        //
        TableQuery<SensorEntity> rangeQuery = TableQuery.from(SensorEntity.class).where(
                TableQuery.combineFilters(
                        TableQuery.generateFilterCondition("PartitionKey", TableQuery.QueryComparisons.EQUAL, sensorName),
                        TableQuery.Operators.AND,
                        TableQuery.generateFilterCondition("RowKey", TableQuery.QueryComparisons.LESS_THAN, endRowKey)));
        //return rangeQuery;
    }

}
