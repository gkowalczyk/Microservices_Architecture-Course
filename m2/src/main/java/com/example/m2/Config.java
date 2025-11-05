package com.example.m2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class Config {

    private final ContextRefresher contextRefresher;

    @Scheduled(fixedDelayString = "${config.refresh.interval-ms:10000}")
    public void refreshConfig() {
        log.info("Checking update from Config Server");
        Set<String> changedProperties = contextRefresher.refresh();
        if (!changedProperties.isEmpty()) {
            log.info("Changed values: {}", changedProperties);
        } else {
            log.info("No configuration changes detected.");
        }
    }
}