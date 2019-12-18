package com.github.corese4rch.cvurl.easytest.asserts.property;

import com.github.corese4rch.cvurl.easytest.domain.EasyRequest;
import coresearch.cvurl.io.internal.configuration.RequestConfiguration;

public class Configuration extends RequestProperty<RequestConfiguration> {

    @Override
    protected String getName() {
        return "configuration";
    }

    @Override
    protected RequestConfiguration mapProperty(EasyRequest request) {
        return request.getConfiguration();
    }
}
