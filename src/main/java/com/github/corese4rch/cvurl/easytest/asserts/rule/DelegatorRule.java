package com.github.corese4rch.cvurl.easytest.asserts.rule;

import java.util.function.Function;

public class DelegatorRule<T, R> extends Rule<T> {

    private Rule<R> delegator;
    private Function<T, R> objMapper;
    private String descriptionPrefix;

    public DelegatorRule(Rule<R> delegator, Function<T, R> objMapper, String descriptionPrefix) {
        this.delegator = delegator;
        this.objMapper = objMapper;
        this.descriptionPrefix = descriptionPrefix;
    }

    @Override
    public boolean isValid(T obj) {
        return delegator.isValid(objMapper.apply(obj));
    }

    @Override
    public String getDescription() {
        return descriptionPrefix + delegator.getDescription();
    }
}
