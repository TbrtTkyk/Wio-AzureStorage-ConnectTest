package com.example.tablestest;

import com.example.tablestest.repository.AzureTableRepository;

public class AzureTest {
    public static void main(String[] args) {
        AzureTableRepository repository = new AzureTableRepository();
        repository.runSample();
    }
}
