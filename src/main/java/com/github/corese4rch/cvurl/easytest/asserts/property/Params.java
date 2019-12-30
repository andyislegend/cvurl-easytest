package com.github.corese4rch.cvurl.easytest.asserts.property;

import com.github.corese4rch.cvurl.easytest.domain.EasyRequest;

import java.util.Map;

public class Params extends RequestProperty<Map<String, String>> {

    @Override
    protected String getName() {
        return "params";
    }

    @Override
    protected Map<String, String> mapProperty(EasyRequest request) {
        return request.getParams();
    }
}
