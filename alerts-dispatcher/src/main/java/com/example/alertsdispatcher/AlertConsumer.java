package com.example.alertsdispatcher;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class AlertConsumer {

    private final List<RamAlert> ramAlerts
            = new CopyOnWriteArrayList<>();


    @KafkaListener(
            topics = "alert",
            groupId = "data-alerts-dispatcher-v2",
            containerFactory = "getAlertsDispatcherFactory"

    )
    public void analyzeMetrics(RamAlert ramAlert) {
        System.out.println("Odebrano alert: " + ramAlert);
        ramAlerts.add(ramAlert);

    }

    public List<RamAlert> getRamAlerts() {
        return List.copyOf(ramAlerts);
    }
}
