package com.example.tablestest.config;

// センサー情報を取得するためのURLを提供するクラス
public class WioURLProvider {

    // 温度センサー情報
    public static String temperature() {
        return "https://cn.wio.seeed.io/v1/node/GroveCo2MhZ16UART0/temperature?access_token="
                + ConfigProperties.getWioAccessTokenTemperature();
    }
}
