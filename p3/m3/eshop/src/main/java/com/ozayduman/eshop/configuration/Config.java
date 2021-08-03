package com.ozayduman.eshop.configuration;

import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

    @Value("${spring.application.name}")
    private String appName;
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }

    @Bean
    public Tracer tracer(){
        return io.jaegertracing.Configuration.fromEnv(appName).getTracer();
    }
}
