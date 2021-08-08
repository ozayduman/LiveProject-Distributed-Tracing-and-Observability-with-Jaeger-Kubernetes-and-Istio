package com.ozayduman.eshop.controller;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;

import java.util.Set;

@UtilityClass
public class HttpHeaderUtility {
    private Set<String> tracingHeaderNames = Set.of(
            "x-request-id",
            "x-b3-traceid",
            "x-b3-spanid",
            "x-b3-parentspanid",
            "x-b3-sampled",
            "x-b3-flags",
            "x-ot-span-context");

    void copyTracingHeaders(HttpHeaders inboundHeaders, HttpHeaders outboundHeaders) {
        tracingHeaderNames.forEach(header -> {
            if(inboundHeaders.containsKey(header)){
                outboundHeaders.add(header, inboundHeaders.getFirst(header));
            }
        });
    }
}
