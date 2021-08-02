package com.ozayduman.logistics.controller;

import org.springframework.http.HttpHeaders;

import java.util.Iterator;
import java.util.Map;

public class HttpHeaderCarrier implements io.opentracing.propagation.TextMap {
    private final HttpHeaders httpHeaders;

    private HttpHeaderCarrier(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public static HttpHeaderCarrier from(HttpHeaders httpHeaders){
        return new HttpHeaderCarrier(httpHeaders);
    }
    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        return httpHeaders.toSingleValueMap().entrySet().iterator();
    }

    @Override
    public void put(String key, String value) {
        httpHeaders.set(key, value);
    }
}
