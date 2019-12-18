package com.github.corese4rch.cvurl.easytest.domain.asserts.rule;

import java.util.List;

public class AndRule<T> extends ComplexRule<T> {

    public AndRule(List<Rule<T>> rules) {
        super(rules);
    }

    @Override
    public boolean isValid(T obj) {
        for (var rule : rules) {
            if (rule.isNotValid(obj)) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected String getSeparator() {
        return "and";
    }
}
