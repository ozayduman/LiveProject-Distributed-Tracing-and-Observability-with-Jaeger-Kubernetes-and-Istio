package com.ozayduman.logistics.controller;

import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
public class LogisticsController {
    private final Tracer tracer;

    @GetMapping(value = "/transport")
    public String transport(){
        val span = tracer.buildSpan("transport").start();
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(3));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            span.finish();
        }
        return "Your Delivery transported";
    }
}
