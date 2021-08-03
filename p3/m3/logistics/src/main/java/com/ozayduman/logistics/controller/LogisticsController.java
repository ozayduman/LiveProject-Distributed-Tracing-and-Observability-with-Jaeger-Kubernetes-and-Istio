package com.ozayduman.logistics.controller;

import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.tag.Tags;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
public class LogisticsController {
    private final Tracer tracer;

    @GetMapping(value = "/transport")
    public String transport(@RequestHeader HttpHeaders httpHeaders){
        SpanContext parent = tracer.extract(Format.Builtin.HTTP_HEADERS, HttpHeaderCarrier.from(httpHeaders));
        val span = tracer.buildSpan("transport").asChildOf(parent).start();
        val user = span.getBaggageItem("user");
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(3));
        } catch (InterruptedException e) {
            Tags.ERROR.set(span, true);
            e.printStackTrace();
        }finally {
            span.finish();
        }
        return "%s's Delivery transported".formatted(user);
    }
}
