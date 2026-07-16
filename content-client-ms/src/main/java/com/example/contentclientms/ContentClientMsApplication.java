package com.example.contentclientms;

import io.micrometer.core.instrument.FunctionCounter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@SpringBootApplication
public class ContentClientMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentClientMsApplication.class, args);

    }
}
//