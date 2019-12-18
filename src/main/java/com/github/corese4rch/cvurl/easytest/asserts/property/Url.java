package com.github.corese4rch.cvurl.easytest.asserts.property;

import com.github.corese4rch.cvurl.easytest.domain.EasyRequest;

public class Url extends RequestProperty<String> {

    @Override
    protected String getName() {
        return "url";
    }

    @Override
    protected String mapProperty(EasyRequest request) {
        return request.getUrl();
    }
}
