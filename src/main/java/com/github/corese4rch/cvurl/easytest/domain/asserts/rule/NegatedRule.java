package com.github.corese4rch.cvurl.easytest.domain.asserts.rule;

public class NegatedRule<T> extends Rule<T> {

    private final Rule<T> rule;

    NegatedRule(Rule<T> rule) {
        this.rule = rule;
    }

    @Override
    public boolean isValid(T obj) {
        return !rule.isValid(obj);
    }

    @Override
    public String getDescription() {
        return "not " + rule.getDescription();
    }
}
