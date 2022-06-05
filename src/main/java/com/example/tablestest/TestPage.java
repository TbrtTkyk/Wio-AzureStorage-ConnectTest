package com.example.tablestest;

import com.example.tablestest.service.WioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestPage {
    private static final Logger log = LoggerFactory.getLogger(TestPage.class);

    @Autowired
    public TestPage() {}

    @GetMapping("test")
    public String hello(HttpServletRequest request) {
        return "Get from " + request.getRequestURI();
    }
}
