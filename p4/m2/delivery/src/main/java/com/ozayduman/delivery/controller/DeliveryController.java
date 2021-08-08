package com.ozayduman.delivery.controller;

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

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DeliveryController {
    private final RestTemplate restTemplate;

    @Value("${logistics.url}")
    private String logisticsUrl;

    @GetMapping(value = "/arrangeDelivery")
    public String arrangeDelivery(@RequestHeader HttpHeaders httpHeaders){

        var result = "";
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(3));
            val outboundHeaders = new HttpHeaders();
            HttpHeaderUtility.copyTracingHeaders(httpHeaders, outboundHeaders);
            HttpEntity<String> entity = new HttpEntity<>(outboundHeaders);
            result = restTemplate.exchange(logisticsUrl, HttpMethod.GET, entity, String.class).getBody();
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return "Delivery Arranged -> %s".formatted(result);
    }
}
