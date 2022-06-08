package com.example.tablestest.config;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.table.CloudTableClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

// application.propertiesから値を取得するクラス
@Configuration
public class ConfigProperties {
    @Value("${azure.connectionString}")
    private String connectionString;

    @Value("${wio.accessToken.temperature}")
    private String accessTokenOfTemperature;

    public String getConnectionString() {
        return connectionString;
    }

    public String getAccessTokenOfTemperature() {
        return accessTokenOfTemperature;
    }

    // CloudTableClientのインスタンスを作成するメソッドの定義
    @Bean
    public CloudTableClient getTableClientReference(ConfigProperties config) throws RuntimeException, URISyntaxException, InvalidKeyException {
        CloudStorageAccount storageAccount;
        try {
            storageAccount = CloudStorageAccount.parse(config.getConnectionString());
        } catch (IllegalArgumentException | URISyntaxException e) {
            System.out.println("\nConnection string specifies an invalid URI.");
            System.out.println("Please confirm the connection string is in the Azure connection string format.");
            throw e;
        } catch (InvalidKeyException e) {
            System.out.println("\nConnection string specifies an invalid key.");
            System.out.println("Please confirm the AccountName and AccountKey in the connection string are valid.");
            throw e;
        }

        return storageAccount.createCloudTableClient();
    }
}
