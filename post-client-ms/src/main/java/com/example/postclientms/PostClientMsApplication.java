package com.example.postclientms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PostClientMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostClientMsApplication.class, args);
    }

}
