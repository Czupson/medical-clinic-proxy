package com.Czupson.medical_clinic_proxy.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.context.annotation.Bean;

public class FeignConfiguration {

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(100, 1000, 3);
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}