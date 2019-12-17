package com.github.corese4rch.cvurl.easytest.domain.asserts.property;

import com.github.corese4rch.cvurl.easytest.domain.EasyRequest;

import java.util.function.Predicate;

public class JsonBodyWithClass<T> extends RequestProperty<T> {

    private Class<T> type;

    public JsonBodyWithClass(Class<T> type) {
        this.type = type;
    }

    @Override
    protected String getName() {
        return "body as " + type.getSimpleName();
    }

    @Override
    protected Predicate<EasyRequest> getPredicate(Predicate<T> propertyPredicate) {
        return request -> {
            T obj = request.getEasyCVurl().getConfiguration().getGenericMapper().readValue(request.getBody(), type);
            return propertyPredicate.test(obj);
        };
    }
}
