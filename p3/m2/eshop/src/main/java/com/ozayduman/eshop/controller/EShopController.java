package com.ozayduman.eshop.controller;

import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.tag.Tags;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
        var result="";
        try {
            val httpHeaders = new HttpHeaders();
            tracer.inject(span.context(), Format.Builtin.HTTP_HEADERS, HttpHeaderCarrier.from(httpHeaders));
            HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
            val inventoryResult = restTemplate.exchange(inventoryUrl, HttpMethod.GET, entity, String.class).getBody();
            val billingResult = restTemplate.exchange(billingUrl, HttpMethod.GET, entity, String.class).getBody();
            val deliveryResult = restTemplate.exchange(deliveryUrl, HttpMethod.GET, entity, String.class).getBody();
            result = String.format("eshop -> %s -> %s -> %s", inventoryResult, billingResult, deliveryResult);
        }catch (Exception e){
            Tags.ERROR.set(span, true);
        }finally {
            span.finish();
        }

        return result;
    }
}
