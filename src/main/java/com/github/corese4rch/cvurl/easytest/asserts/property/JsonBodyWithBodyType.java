package com.github.corese4rch.cvurl.easytest.asserts.property;

import com.github.corese4rch.cvurl.easytest.domain.EasyRequest;
import coresearch.cvurl.io.mapper.BodyType;

public class JsonBodyWithBodyType<T> extends RequestProperty<T> {

    private BodyType<T> type;

    public JsonBodyWithBodyType(BodyType<T> type) {
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
