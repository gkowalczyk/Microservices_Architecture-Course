package com.example.shippingservicezipkin;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShippingController {

    @GetMapping("/api/shipping/status")
    public String shipping() {
        return "Shipping in progress";
    }
}
