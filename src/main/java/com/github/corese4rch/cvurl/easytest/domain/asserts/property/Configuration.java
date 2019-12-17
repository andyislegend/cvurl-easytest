package com.github.corese4rch.cvurl.easytest.domain.asserts.property;

import com.github.corese4rch.cvurl.easytest.domain.EasyRequest;
import coresearch.cvurl.io.internal.configuration.RequestConfiguration;

import java.util.function.Predicate;

public class Configuration extends RequestProperty<RequestConfiguration> {

    @Override
    protected String getName() {
        return "configuration";
    }

    @Override
    protected Predicate<EasyRequest> getPredicate(Predicate<RequestConfiguration> propertyPredicate) {
        return request -> propertyPredicate.test(request.getConfiguration());
    }
}
