package com.example.tablestest.values;

import com.fasterxml.jackson.annotation.JsonCreator;

// 温度センサー情報のJsonデータを格納するクラス
public class TemperatureData {
    double temperature;

    @JsonCreator
    public TemperatureData(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperature() {
        return temperature;
    }
}
