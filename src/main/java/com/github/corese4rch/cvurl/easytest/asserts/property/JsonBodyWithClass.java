package com.github.corese4rch.cvurl.easytest.asserts.property;

import com.github.corese4rch.cvurl.easytest.domain.EasyRequest;

public class JsonBodyWithClass<T> extends RequestProperty<T> {

    private Class<T> type;

    public JsonBodyWithClass(Class<T> type) {
        this.type = type;
    }

    @Override
    protected String getName() {
        return "body";
    }

    @Override
    protected T mapProperty(EasyRequest request) {
        return request.getEasyCVurl()
                .getConfiguration()
                .getGenericMapper()
                .readValue(request.getBody(), type);
    }
}
