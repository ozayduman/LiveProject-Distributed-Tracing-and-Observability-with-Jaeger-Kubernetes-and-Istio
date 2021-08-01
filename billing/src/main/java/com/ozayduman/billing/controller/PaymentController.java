package com.ozayduman.billing.controller;

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
public class PaymentController {
    private final Tracer tracer;

    @GetMapping(value = "/payment")
    public String payment(){
        val span = tracer.buildSpan("payment").start();
        try {
            val random = new Random();
            TimeUnit.SECONDS.sleep(random.nextInt(3));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            span.finish();
        }
        return "Order Payed";
    }
}
