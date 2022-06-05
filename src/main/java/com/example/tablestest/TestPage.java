package com.example.tablestest;

import com.example.tablestest.service.AzureTableService;
import com.example.tablestest.service.WioService;
import com.example.tablestest.values.SensorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestPage {
    private static final Logger log = LoggerFactory.getLogger(TestPage.class);

    private WioService wioService;
    private AzureTableService tableService;

    @Autowired
    public TestPage(WioService wioService, AzureTableService tableService) {
        this.wioService = wioService;
        this.tableService = tableService;
    }

    @GetMapping("test")
    public String hello(HttpServletRequest request) {
        return "Get from " + request.getRequestURI();
    }

    @GetMapping("wioTest")
    public String wioTest(HttpServletRequest request) {
        SensorEntity entity = wioService.getSensorInfo();
        return entity.getPartitionKey() + "(" + entity.getRowKey() + ") : " + entity.getValue();
    }

    @GetMapping("wioSet")
    public String wioSet(HttpServletRequest request) {
        SensorEntity entity = wioService.getSensorInfo();
        tableService.addSensorInfo(entity);
        return "Complete";
    }
}
