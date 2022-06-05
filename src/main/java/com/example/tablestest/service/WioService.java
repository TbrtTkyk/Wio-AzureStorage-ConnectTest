package com.example.tablestest.service;

import com.example.tablestest.repository.WioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// RepositoryからJsonをマッピングしたクラスを取得し、AzureTableへ格納できる形へと変換するService
@Service
public class WioService {

    private WioRepository repository;

    @Autowired
    public WioService(WioRepository repository) {
        this.repository = repository;
    }

    public String getHello() {
        return "Json Message is '" + repository.getHelloWorld().getMessage() + "'";
    }
}
