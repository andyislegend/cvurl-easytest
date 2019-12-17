package com.github.corese4rch.cvurl.easytest.domain.asserts.rule;

import java.util.function.Predicate;

public interface Rule<T> {
    boolean isValid(T obj);

    default boolean isNotValid(T obj) {
        return !isValid(obj);
    }

    String getDescription();

    static <T> RuleImpl<T> of(Predicate<T> predicate, String description) {
        return new RuleImpl<>(predicate, description);
    }
}
