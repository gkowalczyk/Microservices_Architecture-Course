package com.example.orderms;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final OrderRepo orderRepo;

    @PostMapping("/order")
    public String get(@RequestParam String orderId) {
        Order order = new Order(orderId, "Created");
        orderRepo.save(order);
        kafkaTemplate.send("order", orderId);
        return "😊😊😊😊😊😊😊order created" + orderId;
    }

    @KafkaListener(topics = "payment-success",
            groupId = "order-service",
            containerFactory = "factoryOrder"
    )
    public void success(String orderId) {
        orderRepo.findById(orderId)
                .ifPresent(order -> {
                    order.setStatus("Success");
                    orderRepo.save(order);
                    System.out.println("Order payment success");
                });
    }

    @KafkaListener(topics = "payment-failure",
            groupId = "order-service",
            containerFactory = "factoryOrder"
    )
    public void failure(String orderId) {
        orderRepo.findById(orderId)
                .ifPresent(order -> {
                    order.setStatus("Fail");
                    orderRepo.save(order);
                    System.out.println("Order payment fail");
                });
    }

}
