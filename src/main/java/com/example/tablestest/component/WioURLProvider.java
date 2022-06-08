package com.example.tablestest.component;

import com.example.tablestest.config.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// センサー情報を取得するためのURLを提供するクラス
@Component
public class WioURLProvider {
    private String URLForTemperature;

    @Autowired
    public WioURLProvider(ConfigProperties config) {
        URLForTemperature = "https://cn.wio.seeed.io/v1/node/GroveCo2MhZ16UART0/temperature?access_token="
                + config.getAccessTokenOfTemperature();
    }

    public String getURLForTemperature() {
        return URLForTemperature;
    }
}
