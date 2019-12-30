package com.github.corese4rch.cvurl.easytest.asserts.rule;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ComplexRule<T> extends Rule<T> {

    protected final List<Rule<T>> rules;

    ComplexRule(List<Rule<T>> rules) {
        this.rules = rules;
    }

    @Override
    public String getDescription() {
        return rules.stream()
                .map(Rule::getDescription)
                .collect(Collectors.joining(" " + getSeparator() + " "));
    }

    protected abstract String getSeparator();
}
