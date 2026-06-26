package com.example.ordems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class OrdeMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdeMsApplication.class, args);
    }
}