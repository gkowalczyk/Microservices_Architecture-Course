package com.example.sagapatternexample;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PaymentService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public PaymentService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "order",
            groupId = "payment-service",
            containerFactory = "factory")
    public String get(String orderId) {

        boolean success = new Random().nextBoolean();

        if (success) {
            kafkaTemplate.send("payment-success", orderId);
            System.out.println("payment-success" + orderId);
        } else {
            kafkaTemplate.send("payment-failure", orderId);
            System.out.println("payment-failute" + orderId);
        }
        return "";
    }
}
