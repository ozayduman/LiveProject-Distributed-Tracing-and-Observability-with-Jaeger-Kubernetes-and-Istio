package com.ozayduman.delivery.controller;

import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class DeliveryController {
    private final RestTemplate restTemplate;
    private final Tracer tracer;

    @Value("${logistics.url}")
    private String logisticsUrl;

    @GetMapping(value = "/arrangeDelivery")
    public String arrangeDelivery(){
        val span = tracer.buildSpan("arrangeDelivery").start();
        val result = restTemplate.getForObject(logisticsUrl, String.class);
        span.finish();
        return "Delivery Arranged -> %s".formatted(result);
    }
}
