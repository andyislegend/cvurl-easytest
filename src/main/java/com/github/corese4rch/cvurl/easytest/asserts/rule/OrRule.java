package com.github.corese4rch.cvurl.easytest.asserts.rule;

import java.util.List;

public class OrRule<T> extends ComplexRule<T> {

    public OrRule(List<Rule<T>> rules) {
        super(rules);
    }

    @Override
    public boolean isValid(T obj) {
        for (Rule<T> rule : rules) {
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
