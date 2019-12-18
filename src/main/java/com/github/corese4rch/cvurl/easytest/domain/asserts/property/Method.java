package com.github.corese4rch.cvurl.easytest.domain.asserts.property;

import com.github.corese4rch.cvurl.easytest.domain.EasyRequest;
import coresearch.cvurl.io.constant.HttpMethod;

public class Method extends RequestProperty<HttpMethod> {

    @Override
    protected String getName() {
        return "method";
    }

    @Override
    protected HttpMethod mapProperty(EasyRequest request) {
        return request.getHttpMethod();
    }
}
