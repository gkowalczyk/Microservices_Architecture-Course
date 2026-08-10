package com.example.alertsdispatcher;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AlarmController {

    private final AlertConsumer alertConsumer;

    public AlarmController(AlertConsumer alertConsumer) {
        this.alertConsumer = alertConsumer;
    }

    @GetMapping("/get-alerts")
    public ResponseEntity<List<RamAlert>> getAlerts(@RequestParam(required = false) String level) {
        List<RamAlert> ramAlertList = alertConsumer.getRamAlerts();

        System.out.println(ramAlertList);
        ramAlertList.stream()
                .filter(alert -> alert.getMessage()!= null)
                .filter(element -> element.getMessage().equalsIgnoreCase(level))
                .toList();
        return ResponseEntity.ok(ramAlertList);
    }
}