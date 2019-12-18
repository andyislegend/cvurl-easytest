package com.github.corese4rch.cvurl.easytest.domain.asserts.property;

import com.github.corese4rch.cvurl.easytest.domain.EasyRequest;

import java.util.Map;

public class Headers extends RequestProperty<Map<String, String>> {

    @Override
    protected String getName() {
        return "headers";
    }

    @Override
    protected Map<String, String> mapProperty(EasyRequest request) {
        return request.getHeaders();
    }
}
