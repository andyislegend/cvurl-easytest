package com.github.corese4rch.cvurl.easytest.asserts.property;

import com.github.corese4rch.cvurl.easytest.domain.EasyRequest;

import java.util.function.Function;

public class Body<T> extends RequestProperty<T> {

    private final Function<String, T> bodyMapper;

    Body(Function<String, T> bodyMapper) {
        this.bodyMapper = bodyMapper;
    }

    @Override
    protected String getName() {
        return "body";
    }

    @Override
    protected T mapProperty(EasyRequest request) {
        String body = request.getBody();
        return body == null ? null : bodyMapper.apply(body);
    }
}
