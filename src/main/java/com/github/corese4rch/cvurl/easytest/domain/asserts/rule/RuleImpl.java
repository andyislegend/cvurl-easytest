package com.github.corese4rch.cvurl.easytest.domain.asserts.rule;

import lombok.Getter;

import java.util.function.Predicate;

public class RuleImpl<T> implements Rule<T> {

    @Getter
    private final String description;
    private final Predicate<T> predicate;

    RuleImpl(Predicate<T> predicate, String description) {
        this.predicate = predicate;
        this.description = description;
    }

    @Override
    public boolean isValid(T obj) {
        return predicate.test(obj);
    }
}
