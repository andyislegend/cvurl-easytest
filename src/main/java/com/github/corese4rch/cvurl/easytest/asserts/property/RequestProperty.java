package com.github.corese4rch.cvurl.easytest.asserts.property;

import com.github.corese4rch.cvurl.easytest.asserts.rule.Rule;
import com.github.corese4rch.cvurl.easytest.asserts.rule.Rules;
import com.github.corese4rch.cvurl.easytest.domain.EasyRequest;

import java.util.function.Predicate;

/**
 * RequestProperty is wrapper around Rule that puts it into context of some property of the request.
 *
 * @param <T>
 */
public abstract class RequestProperty<T> {
    public Rule<EasyRequest> is(Rule<T> propertyRule) {
        return Rules.of(propertyRule, this::mapProperty, getName() + " ");
    }

    public Rule<EasyRequest> is(Predicate<T> predicate, String description) {
        return is(Rules.of(predicate, description));
    }

    public Rule<EasyRequest> isNot(Rule<T> propertyRule) {
        return Rules.of(Rules.negate(propertyRule), this::mapProperty, getName() + " ");
    }

    public Rule<EasyRequest> isNot(Predicate<T> predicate, String description) {
        return isNot(Rules.of(predicate, description));
    }

    protected abstract String getName();

    protected abstract T mapProperty(EasyRequest request);
}
