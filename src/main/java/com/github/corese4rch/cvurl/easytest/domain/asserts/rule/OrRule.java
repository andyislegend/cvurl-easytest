package com.github.corese4rch.cvurl.easytest.domain.asserts.rule;

import java.util.List;

public class OrRule<T> extends ComplexRule<T> {

    public OrRule(List<Rule<T>> rules) {
        super(rules);
    }

    @Override
    public boolean isValid(T obj) {
        for (var rule : rules) {
            if (rule.isValid(obj)) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected String getSeparator() {
        return "or";
    }
}
