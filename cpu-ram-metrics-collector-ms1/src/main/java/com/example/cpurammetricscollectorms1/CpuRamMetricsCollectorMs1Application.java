package com.example.cpurammetricscollectorms1;

import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootApplication
public class CpuRamMetricsCollectorMs1Application {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(CpuRamMetricsCollectorMs1Application.class, args);
    }
    }