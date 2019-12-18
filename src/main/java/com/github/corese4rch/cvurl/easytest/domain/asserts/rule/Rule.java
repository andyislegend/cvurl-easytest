package com.github.corese4rch.cvurl.easytest.domain.asserts.rule;

public abstract class Rule<T> {
    public abstract boolean isValid(T obj);

    public abstract String getDescription();

    public final boolean isNotValid(T obj) {
        return !isValid(obj);
    }
}
