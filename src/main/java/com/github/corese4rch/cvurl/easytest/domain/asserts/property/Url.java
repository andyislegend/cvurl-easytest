package com.github.corese4rch.cvurl.easytest.domain.asserts.property;

import com.github.corese4rch.cvurl.easytest.domain.EasyRequest;

import java.util.function.Predicate;

public class Url extends RequestProperty<String> {

    @Override
    protected String getName() {
        return "url";
    }

    @Override
    protected Predicate<EasyRequest> getPredicate(Predicate<String> propertyPredicate) {
        return request -> propertyPredicate.test(request.getUrl());
    }
}
