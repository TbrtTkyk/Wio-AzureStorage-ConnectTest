package com.example.tablestest.repository;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.table.CloudTableClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.Properties;

// Azure StorageのTableにアクセスするCloudTableClientを作成するクラス
// 以下ソースコード参照
// https://github.com/Azure-Samples/storage-table-java-getting-started/
public class TableClientProvider {
    private static Properties prop;

    static {
        prop = new Properties();
        try {
            InputStream propertyStream = TableClientProvider.class.getClassLoader().getResourceAsStream("config.properties");
            if (propertyStream != null) {
                prop.load(propertyStream);
            } else {
                throw new RuntimeException();
            }
        } catch (RuntimeException | IOException e) {
            System.out.println("\nFailed to load config.properties file.");
            throw new RuntimeException();
        }
    }

    static CloudTableClient getTableClientReference() throws RuntimeException, URISyntaxException, InvalidKeyException {
        CloudStorageAccount storageAccount;
        try {
            storageAccount = CloudStorageAccount.parse(prop.getProperty("StorageConnectionString"));
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

    public static boolean isAzureCosmosdbTable() {
        if (prop != null) {
            String connectionString = prop.getProperty("StorageConnectionString");
            return connectionString != null && connectionString.contains("table.cosmosdb");
        }
        return false;
    }
}
