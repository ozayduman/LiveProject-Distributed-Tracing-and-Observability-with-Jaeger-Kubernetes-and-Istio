package com.ozayduman.eshop.controller;

import io.opentracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class EShopController {

    @Value("${inventory.url}")
    private String inventoryUrl;
    @Value("${billing.url}")
    private String billingUrl;
    @Value("${delivery.url}")
    private String deliveryUrl;

    private final RestTemplate restTemplate;

    private final Tracer tracer;

    @GetMapping(value = "/checkout")
    public String checkout(){
        val span = tracer.buildSpan("checkout").start();

        val inventoryResult = restTemplate.getForObject(inventoryUrl, String.class);
        val billingResult = restTemplate.getForObject(billingUrl, String.class);
        val deliveryResult = restTemplate.getForObject(deliveryUrl, String.class);
        val result = String.format("eshop -> %s -> %s -> %s", inventoryResult, billingResult, deliveryResult);

        span.finish();
        return result;
    }
}
