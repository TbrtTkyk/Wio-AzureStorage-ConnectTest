package com.example.tablestest.values;

import com.fasterxml.jackson.annotation.JsonCreator;

// 温度センサー情報のJsonデータを格納するクラス
public class TemperatureData {

    double value;

    @JsonCreator
    public TemperatureData(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
