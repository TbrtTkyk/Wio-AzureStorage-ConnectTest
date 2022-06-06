package com.example.tablestest.repository;

import com.example.tablestest.config.ConfigProperties;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.table.CloudTableClient;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

// Azure StorageのTableにアクセスするCloudTableClientを作成するクラス
public class TableClientProvider {
    static CloudTableClient getTableClientReference() throws RuntimeException, URISyntaxException, InvalidKeyException {
        CloudStorageAccount storageAccount;
        try {
            storageAccount = CloudStorageAccount.parse(ConfigProperties.getStorageConnectionString());
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
