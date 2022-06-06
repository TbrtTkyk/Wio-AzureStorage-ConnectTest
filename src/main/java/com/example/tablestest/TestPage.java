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

    @GetMapping("test/")
    public String hello(HttpServletRequest request) {
        return "Get from " + request.getRequestURI();
    }

    @GetMapping("test/wio/get")
    public String wioTest(HttpServletRequest request) {
        SensorEntity entity = wioService.getSensorInfo();
        return toStringSensorEntity(entity);
    }

    @GetMapping("test/azure/previous")
    public String mostPrevious(HttpServletRequest request) {
        SensorEntity entity = tableService.getMostPrevious();
        return toStringSensorEntity(entity);
    }

    @GetMapping("test/set")
    public String wioSet(HttpServletRequest request) {
        SensorEntity entity = wioService.getSensorInfo();
        if( entity == null ) {
            return "Failed to get Sensor Information...";
        }
        try {
            tableService.addSensorInfo(entity);
            return "Failed to set Sensor Information...";
        } catch (Throwable t){
            return "Complete to set Sensor Information!";
        }
    }

    @GetMapping("test/delete")
    public String deleteOverSize(HttpServletRequest request) {
        try {
            tableService.deleteOverSize();
            return "Complete to delete!";
        } catch (Throwable t) {
            return "Failed to delete...";
        }
    }

    // SensorEntityを見やすいように文字列に変換
    private String toStringSensorEntity(SensorEntity entity) {
        if (entity == null) {
            return "Failed to get Sensor Information...";
        } else {
            return entity.getPartitionKey() + "(" + entity.getRowKey() + ") : " + entity.getValue();
        }
    }
}
