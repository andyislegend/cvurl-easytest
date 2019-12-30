package com.github.corese4rch.cvurl.easytest.asserts.property;

import coresearch.cvurl.io.constant.HttpMethod;
import coresearch.cvurl.io.internal.configuration.RequestConfiguration;
import coresearch.cvurl.io.mapper.BodyType;

import java.util.Map;
import java.util.function.Function;

public class RequestProperties {

    public static RequestProperty<String> body() {
        return new Body<>(Function.identity());
    }

    public static <T> RequestProperty<T> body(Function<String, T> bodyMapper) {
        return new Body<>(bodyMapper);
    }

    public static <T> RequestProperty<T> body(Class<T> type) {
        return new JsonBodyWithClass<>(type);
    }

    public static <T> RequestProperty<T> body(BodyType<T> type) {
        return new JsonBodyWithBodyType<>(type);
    }

    public static RequestProperty<String> url() { return new Url(); }

    public static RequestProperty<Map<String, String>> params() {
        return new Params();
    }

    public static RequestProperty<Map<String, String>> headers() { return new Headers(); }

    public static RequestProperty<RequestConfiguration> configuration() {
        return new Configuration();
    }

    public static RequestProperty<HttpMethod> method() {
        return new Method();
    }
}
