package com.ozayduman.inventory.controller;

import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@AllArgsConstructor
public class InventoryController {
    private final Tracer tracer;

    @RequestMapping(value = "/createOrder")
    public String createOrder(){
        val span = tracer.buildSpan("createOrder").start();
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(3));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            span.finish();
        }
        return "Order Created";
    }
}
