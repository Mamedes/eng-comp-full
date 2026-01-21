package com.seletivo.infra.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api.ratelimit")
public class RateLimitProperties {

    private int capacity;
    private int timeUnitMinutes;

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getTimeUnitMinutes() { return timeUnitMinutes; }
    public void setTimeUnitMinutes(int timeUnitMinutes) { this.timeUnitMinutes = timeUnitMinutes; }
}