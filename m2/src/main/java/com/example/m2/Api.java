package com.example.m2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RefreshScope
@RestController
public class Api {

    @Value("${message}")
    private String message;


    @GetMapping("/hello")
    public String get() {
        return message;
    }



}
