package com.github.corese4rch.cvurl.easytest.domain.asserts.property;

import com.github.corese4rch.cvurl.easytest.domain.EasyRequest;

import java.util.Map;
import java.util.function.Predicate;

public class Params extends RequestProperty<Map<String, String>> {

    @Override
    protected String getName() {
        return "params";
    }

    @Override
    protected Predicate<EasyRequest> getPredicate(Predicate<Map<String, String>> propertyPredicate) {
        return request -> propertyPredicate.test(request.getParams());
    }
}
