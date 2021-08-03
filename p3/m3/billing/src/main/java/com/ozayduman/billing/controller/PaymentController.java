package com.ozayduman.billing.controller;

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
public class PaymentController {
    private final Tracer tracer;

    @GetMapping(value = "/payment")
    public String payment(@RequestHeader HttpHeaders httpHeaders){
        SpanContext parent = tracer.extract(Format.Builtin.HTTP_HEADERS, HttpHeaderCarrier.from(httpHeaders));
        val span = tracer.buildSpan("payment").asChildOf(parent).start();
        val user = span.getBaggageItem("user");
        try {
            val random = new Random();
            TimeUnit.SECONDS.sleep(random.nextInt(3));
        } catch (InterruptedException e) {
            Tags.ERROR.set(span, true);
            e.printStackTrace();
        } finally {
            span.finish();
        }
        return "%s's Order Payed".formatted(user);
    }
}
