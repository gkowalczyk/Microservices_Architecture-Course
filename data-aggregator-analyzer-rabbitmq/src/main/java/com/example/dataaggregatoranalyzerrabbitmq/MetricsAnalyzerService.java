package com.example.dataaggregatoranalyzerrabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetricsAnalyzerService {

    private final RabbitTemplate rabbitTemplate;

    public static final String METRICS_QUEUE =
            "system-metrics.queue";


    public static final String ALERTS_EXCHANGE =
            "alerts.exchange";


    public static final String ALERTS_ROUTING_KEY =
            "ram.alert";

        @RabbitListener(
                queues = METRICS_QUEUE)
        public void analyzeMetrics(SystemMetrics systemMetrics) {
            log.info("Odebrano metryki: {}", systemMetrics);
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

           rabbitTemplate.convertAndSend(
                   ALERTS_EXCHANGE,
                   ALERTS_ROUTING_KEY,
                   ramAlert
           );
            log.info("Wysłano alert: {}", ramAlert);
        }
    }