package com.example.dataaggregatoranalyzerrabbitmq;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SystemMetrics {

    private double cpuPower;
    private double cpuPercentage;
    private double totalRam;
    private double ramUsagePercentage;
    private LocalDateTime time;
}
