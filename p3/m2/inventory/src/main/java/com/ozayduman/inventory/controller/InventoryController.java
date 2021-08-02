package com.ozayduman.inventory.controller;

import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.tag.Tags;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@AllArgsConstructor
public class InventoryController {
    private final Tracer tracer;

    @RequestMapping(value = "/createOrder")
    public String createOrder(@RequestHeader HttpHeaders httpHeaders){
        SpanContext parent = tracer.extract(Format.Builtin.HTTP_HEADERS, HttpHeaderCarrier.from(httpHeaders));

        val span = tracer.buildSpan("createOrder").asChildOf(parent).start();
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(3));
        } catch (InterruptedException e) {
            Tags.ERROR.set(span, true);
            e.printStackTrace();
        }finally {
            span.finish();
        }
        return "Order Created";
    }
}
