package com.example.dataaggregatoranalyzer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetricsAnalyzerService {
        private final KafkaTemplate<String, RamAlert> kafkaTemplate;

        @KafkaListener(
                topics = "system-metrics",
                groupId = "data-aggregator-group"
        )
        public void analyzeMetrics(SystemMetrics systemMetrics) {

            double ramUsagePercentage =
                    systemMetrics.getRamUsagePercentage();

            RamAlert ramAlert;

            if (ramUsagePercentage >= 70) {

                ramAlert = new RamAlert(
                        "alert",
                        ramUsagePercentage,
                        systemMetrics.getTime()
                );

            } else if (ramUsagePercentage >= 50) {

                ramAlert = new RamAlert(
                        "warning",
                        ramUsagePercentage,
                        systemMetrics.getTime()
                );

            } else {

                return;
            }

            kafkaTemplate.send("alert", ramAlert)
                    .thenAccept(result -> {
                        System.out.println(
                                "Wysłano: " + ramAlert
                        );

                        System.out.println(
                                "Partycja: "
                                        + result.getRecordMetadata().partition()
                        );

                        System.out.println(
                                "Offset: "
                                        + result.getRecordMetadata().offset()
                        );
                    })
                    .exceptionally(exception -> {
                        System.out.println(
                                "Nie udało się wysłać alertu: "
                                        + exception.getMessage()
                        );
                        return null;
                    });
        }
    }