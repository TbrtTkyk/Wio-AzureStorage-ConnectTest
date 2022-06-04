package com.example.tablestest.values;

import com.microsoft.azure.storage.table.TableServiceEntity;

public class SensorEntity extends TableServiceEntity {
    public SensorEntity(String SensorName, String time) {
        this.partitionKey = SensorName;
        this.rowKey = time;
    }
    public SensorEntity() { }

    private double value;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
