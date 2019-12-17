package com.github.corese4rch.cvurl.easytest.domain;

import coresearch.cvurl.io.constant.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EasyRequest {
    private String url;
    private Map<String, String> params = new HashMap<>();
    private Map<String, String> headers = new HashMap<>();
    private String body;
    private HttpMethod httpMethod;
    private MutableRequestConfiguration configuration = new MutableRequestConfiguration();
    private AtomicInteger timesExecuted = new AtomicInteger(0);
    private EasyCVurl easyCVurl;

    public EasyRequest(String url, HttpMethod httpMethod, EasyCVurl easyCVurl) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.easyCVurl = easyCVurl;
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public void addParam(String key, String value) {
        this.params.put(key, value);
    }

    public void incrementExecuted() {
        timesExecuted.incrementAndGet();
    }

    public int getTimesExecuted() {
        return timesExecuted.get();
    }
}
