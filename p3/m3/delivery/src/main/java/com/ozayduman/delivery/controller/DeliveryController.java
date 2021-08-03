package com.ozayduman.delivery.controller;

import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import lombok.RequiredArgsConstructor;
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
public class DeliveryController {
    private final RestTemplate restTemplate;
    private final Tracer tracer;

    @Value("${logistics.url}")
    private String logisticsUrl;

    @GetMapping(value = "/arrangeDelivery")
    public String arrangeDelivery(@RequestHeader HttpHeaders httpHeaders){
        SpanContext parent = tracer.extract(Format.Builtin.HTTP_HEADERS, HttpHeaderCarrier.from(httpHeaders));
        val span = tracer.buildSpan("arrangeDelivery").asChildOf(parent).start();
        val user = span.getBaggageItem("user");
        var result = "";
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(3));
            val outboundHeaders = new HttpHeaders();
            tracer.inject(span.context(), Format.Builtin.HTTP_HEADERS, HttpHeaderCarrier.from(outboundHeaders));
            HttpEntity<String> entity = new HttpEntity<>(outboundHeaders);
            result = restTemplate.exchange(logisticsUrl, HttpMethod.GET, entity, String.class).getBody();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            span.finish();
        }
        return "%s's Delivery Arranged -> %s".formatted(user, result);
    }
}
