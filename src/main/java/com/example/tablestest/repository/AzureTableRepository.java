package com.example.tablestest.repository;

import com.example.tablestest.values.SensorEntity;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class AzureTableRepository {
    protected static CloudTableClient tableClient = null;
    protected static CloudTable table = null;

    // SensorEntityクラスを日付で比較するComparator
    private static final Comparator<SensorEntity> dateComparator = Comparator.comparing(SensorEntity::getTimestamp);

    @Autowired
    public AzureTableRepository() throws RuntimeException, URISyntaxException, InvalidKeyException, StorageException {
        // Tableサービスに干渉するためのTableClientを作成
        tableClient = TableClientProvider.getTableClientReference();
        // SensorsTestという名前のテーブルを取得
        table = tableClient.getTableReference("SensorsTest");
    }

    // 最新の指定のセンサー情報を取得する
    public SensorEntity getMostPrevious(String sensorName) {
        // 指定のセンサーのデータを取得
        Iterable<SensorEntity> result = getSensorEntities(sensorName);

        // データの中から最も最新のものを取得する（データが空ならnullを取得する）
        SensorEntity mostPrevious = StreamSupport.stream(result.spliterator(), false)
                .max(dateComparator)
                .orElse(null);

        return mostPrevious;
    }

    // 複数のSensorEntityをまとめて挿入する
    public void batchInsertOfSensorEntities(List<SensorEntity> entities) throws StorageException{
        TableBatchOperation batchOperation = new TableBatchOperation();
        for (SensorEntity entity : entities) {
            batchOperation.insertOrMerge(entity);
        }
        table.execute(batchOperation);
    }
    // 1つのSensorEntityを挿入する
    public void insertOfSensorEntity(SensorEntity entity) throws StorageException {
        table.execute(TableOperation.insertOrMerge(entity));
    }

    // 指定のデータ数を超える場合、古いものから削除する
    public void deleteOverSize(String sensorName, int size) throws StorageException{
        // 削除するデータを取得する
        List<SensorEntity> list = getOlderByIndex(sensorName, size);
        // 削除するデータがないなら戻る
        if ( list == null ) return;
        // 削除処理
        TableBatchOperation batchOperation = new TableBatchOperation();
        for (SensorEntity entity : list) {
            batchOperation.delete(entity);
        }
        table.execute(batchOperation);
    }

    // 指定のセンサーの計測値の平均を取得する
    public double avgSensorValue(String sensorName) {
        // 指定のセンサー情報を取得
        Iterable<SensorEntity> entities = getSensorEntities(sensorName);

        // 平均を計算（データが空のときは0を返す）
        Double avg = StreamSupport.stream(entities.spliterator(), false)
                .collect(Collectors.averagingDouble(SensorEntity::getValue));
        return avg;
    }


    // 新しいものから順に、指定番目以降のデータを取得する
    private List<SensorEntity> getOlderByIndex(String sensorName, int index) {
        // 指定のセンサー情報を取得
        Iterable<SensorEntity> entities = getSensorEntities(sensorName);

        // データを古いものから順にソート
        List<SensorEntity> olderSortList = StreamSupport.stream(entities.spliterator(), false)
                .sorted(dateComparator)
                .collect(Collectors.toList());
        // 指定番目より古いデータを返す
        if ( olderSortList.size() <= index ) {
            return null;
        } else {
            return olderSortList.subList(0, olderSortList.size() - index);
        }
    }
    // 新しいものから順に、指定番目のデータを取得する
    private SensorEntity getByIndex(String sensorName, int index) {
        // 指定のセンサー情報を取得
        Iterable<SensorEntity> entities = getSensorEntities(sensorName);

        // 新しいものから順に指定番目までのデータを取得
        List<SensorEntity> list = StreamSupport.stream(entities.spliterator(), false)
                .sorted(dateComparator.reversed())
                .limit(index + 1)
                .collect(Collectors.toList());
        // 指定番目までデータが無いならnull、あるならそれを返す
        if ( list.size() < index + 1 ) {
            return null;
        } else {
            return list.get(index);
        }
    }

    // 指定のセンサーのデータを取得する
    private Iterable<SensorEntity> getSensorEntities(String sensorName) {
        // 指定のセンサーのデータを取得するQuery文の作成
        TableQuery<SensorEntity> query = TableQuery.from(SensorEntity.class).where(
                TableQuery.generateFilterCondition("PartitionKey", TableQuery.QueryComparisons.EQUAL, sensorName));
        // Query文の実行
        return table.execute(query);
    }
}
