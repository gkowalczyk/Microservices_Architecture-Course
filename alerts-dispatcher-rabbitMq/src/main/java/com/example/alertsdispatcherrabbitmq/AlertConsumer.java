package com.example.alertsdispatcherrabbitmq;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class AlertConsumer {

    public static final String ALERTS_QUEUE =
            "alerts.queue";

    private final List<RamAlert> ramAlerts
            = new CopyOnWriteArrayList<>();


    @RabbitListener(
            queues = ALERTS_QUEUE)

    public void analyzeMetrics(RamAlert ramAlert) {
        System.out.println("Odebrano alert: " + ramAlert);
        ramAlerts.add(ramAlert);

    }

    public List<RamAlert> getRamAlerts() {
        return List.copyOf(ramAlerts);
    }
}
