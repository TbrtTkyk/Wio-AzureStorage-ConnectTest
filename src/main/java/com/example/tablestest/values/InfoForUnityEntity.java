package com.example.tablestest.values;

import com.microsoft.azure.storage.table.TableServiceEntity;

import java.util.Objects;

// Unity側で扱うデータテーブルに格納する用のTableServiceEntity
public class InfoForUnityEntity extends TableServiceEntity {
    public InfoForUnityEntity(String type, String valueName) {
        this.partitionKey = type;
        this.rowKey = valueName;
        this.value = 0;
        this.status = EnumInfoStatus.NODATA.name();
    }
    public InfoForUnityEntity() { }

    // センサーの現在の計測値
    public double value;
    // 状態を示す文字列
    public String status;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
        if(Objects.equals(status, EnumInfoStatus.NODATA.name())) {
            status = EnumInfoStatus.STABLE.name();
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
