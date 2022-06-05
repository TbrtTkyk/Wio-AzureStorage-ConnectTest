package com.example.tablestest.values;

import com.fasterxml.jackson.annotation.JsonCreator;

// 温度センサー情報のJsonデータを格納するクラス
public class TemperatureData {
    // JSONデータを取得するためのURL
    public static final String url = "MY_WIO_URL";

    double temperature;

    @JsonCreator
    public TemperatureData(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperature() {
        return temperature;
    }
}
