package com.example.cpurammetricscollectorms1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;

@RestController
@RequestMapping("/metrics")
@RequiredArgsConstructor
@Slf4j
public class SystemMetricsController {


    private final SystemInfo systemInfo = new SystemInfo();
    private final HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
    private final CentralProcessor centralProcessor = hardwareAbstractionLayer.getProcessor();
    private final GlobalMemory memory = hardwareAbstractionLayer.getMemory();
    private long[] prevTicks = centralProcessor.getSystemCpuLoadTicks();
    private final KafkaTemplate<String, SystemMetrics> kafkaTemplate;


    @GetMapping("/system")
    public SystemMetrics getSystemMetrics() {
        long[] newTicks = centralProcessor.getSystemCpuLoadTicks(); //actual processor counter
        double cpuLoad = centralProcessor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
        prevTicks = newTicks;
        long[] frequencies = centralProcessor.getCurrentFreq();
        double avgFreq = frequencies.length > 0 ?
                Arrays.stream(frequencies)
                        .average()
                        .orElse(0.0)
                        / 1_000_000_000.0 : 0.0;
        long totalMemory = memory.getTotal();
        long usedMemory = totalMemory - memory.getAvailable();
        double totalRamGb =
                totalMemory / (1024.0 * 1024.0 * 1024.0);
        double ramUsagePercentage =
                totalMemory > 0
                        ? (double) usedMemory / totalMemory * 100
                        : 0.0;

        SystemMetrics systemMetrics =
         new SystemMetrics(
                round(avgFreq),
                round(cpuLoad),
                round(totalMemory / (1024.0 * 1024 * 1024)),
                round(ramUsagePercentage),
                LocalDateTime.now()
        );

        kafkaTemplate.send("system-metrics", systemMetrics)
                .thenAccept(result -> {
                    log.info("Send message");
                    log.info("Partition" + result.getProducerRecord().partition());
                    log.info("Offset" + result.getRecordMetadata().offset());
                }).exceptionally(exception -> {
                    log.error("Error send" + exception.getMessage());
                    return null;
                });

        return systemMetrics;
    }


    private double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
