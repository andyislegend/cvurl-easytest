package com.github.corese4rch.cvurl.easytest.domain.asserts.property;

import com.github.corese4rch.cvurl.easytest.domain.EasyRequest;
import coresearch.cvurl.io.constant.HttpMethod;

import java.util.function.Predicate;

public class Method extends RequestProperty<HttpMethod> {

    @Override
    protected String getName() {
        return "method";
    }

    @Override
    protected Predicate<EasyRequest> getPredicate(Predicate<HttpMethod> propertyPredicate) {
        return request -> propertyPredicate.test(request.getHttpMethod());
    }
}
