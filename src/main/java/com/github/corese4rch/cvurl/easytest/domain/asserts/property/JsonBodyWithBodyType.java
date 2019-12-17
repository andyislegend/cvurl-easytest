package com.github.corese4rch.cvurl.easytest.domain.asserts.property;

import com.github.corese4rch.cvurl.easytest.domain.EasyRequest;
import coresearch.cvurl.io.mapper.BodyType;

import java.util.function.Predicate;

public class JsonBodyWithBodyType<T> extends RequestProperty<T> {

    private BodyType<T> type;

    public JsonBodyWithBodyType(BodyType<T> type) {
        this.type = type;
    }

    @Override
    protected String getName() {
        return "body as " + type.getType().getTypeName();
    }

    @Override
    protected Predicate<EasyRequest> getPredicate(Predicate<T> propertyPredicate) {
        return request -> {
            T obj = request.getEasyCVurl().getConfiguration().getGenericMapper().readValue(request.getBody(), type);
            return propertyPredicate.test(obj);
        };
    }
}
