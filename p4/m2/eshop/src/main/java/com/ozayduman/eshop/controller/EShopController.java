package com.ozayduman.eshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EShopController {

    @Value("${inventory.url}")
    private String inventoryUrl;
    @Value("${billing.url}")
    private String billingUrl;
    @Value("${delivery.url}")
    private String deliveryUrl;

    private final RestTemplate restTemplate;

    @GetMapping(value = "/checkout")
    public String checkout(@RequestHeader HttpHeaders httpHeaders){
        var result="";
        try {
            val outboundHeaders = new HttpHeaders();
            HttpHeaderUtility.copyTracingHeaders(httpHeaders, outboundHeaders);
            HttpEntity<String> entity = new HttpEntity<>(outboundHeaders);
            val inventoryResult = restTemplate.exchange(inventoryUrl, HttpMethod.GET, entity, String.class).getBody();
            val billingResult = restTemplate.exchange(billingUrl, HttpMethod.GET, entity, String.class).getBody();
            val deliveryResult = restTemplate.exchange(deliveryUrl, HttpMethod.GET, entity, String.class).getBody();
            result = String.format("eshop -> %s -> %s -> %s", inventoryResult, billingResult, deliveryResult);
        }catch (Exception e){
           log.error(e.getMessage());
        }
        return result;
    }
}
