package com.example.dataaggregatoranalyzerrabbitmq;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RamAlert {
    private String message;
    private double realUsage;
    private LocalDateTime time;
}