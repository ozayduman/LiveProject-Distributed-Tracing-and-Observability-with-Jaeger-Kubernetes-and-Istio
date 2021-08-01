package com.ozayduman.billing.configuration;

import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public Tracer tracer(){
        return io.jaegertracing.Configuration.fromEnv(appName).getTracer();
    }
}
