package com.example.alertsdispatcher;

import java.time.LocalDateTime;


public class RamAlert {
    private String message;
    private double realUsage;
    private LocalDateTime time;

    public RamAlert(String message, double realUsage, LocalDateTime time) {
        this.message = message;
        this.realUsage = realUsage;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public Double getRealUsage() {
        return realUsage;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public RamAlert() {
    }
}
