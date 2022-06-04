package com.example.tablestest.repository;

import com.example.tablestest.values.SensorEntity;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableBatchOperation;

import java.time.LocalDateTime;

public class AzureTableRepository {
    protected static CloudTableClient tableClient = null;
    protected static CloudTable table = null;

    public void runSample() {
        System.out.println("Azure Storage Table sample - Starting.");

        try {
            // Tableサービスに干渉するためのTableClientを作成
            tableClient = TableClientProvider.getTableClientReference();

            // SensorsTestという名前のテーブルを取得
            table = tableClient.getTableReference("SensorsTest");

            // テーブルに値を追加
            batchInsertOfSensorEntities(table);

        } catch (Throwable t) {
            t.printStackTrace();
            if (t instanceof StorageException) {
                if (((StorageException) t).getExtendedErrorInformation() != null) {
                    System.out.println(String.format("\nError: %s", ((StorageException) t).getExtendedErrorInformation().getErrorMessage()));
                }
            }
        }

        System.out.println("\nAzure Storage Table sample - Completed.\n");
    }

    private static void batchInsertOfSensorEntities(CloudTable table) throws StorageException{
        TableBatchOperation batchOperation1 = new TableBatchOperation();
        String ldt = LocalDateTime.now().toString();

        SensorEntity entity = new SensorEntity("Temperature", ldt);
        entity.setValue(0.0);
        batchOperation1.insertOrMerge(entity);

        table.execute(batchOperation1);
    }


}
