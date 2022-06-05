package com.example.tablestest.values;

import com.fasterxml.jackson.annotation.JsonCreator;

public class HelloWorld {
    private String message;

    @JsonCreator
    public HelloWorld(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
