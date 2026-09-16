package com.example.orderservicezipkin;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final RestClient restClient;
    private final Tracer tracer;

    @GetMapping("/order")
    public String get(@RequestParam String item, @RequestParam String paymentMethod) {

        Span span = tracer.nextSpan().name("order-processing").start();
        span.tag("item", item);

        try {
            String response = restClient.get()
                    .uri("/api/payment/status?paymentMethod={paymentMethod}", paymentMethod)
                    .retrieve()
                    .body(String.class);
            return response;
        } finally {

            span.end();
        }
    }
}
