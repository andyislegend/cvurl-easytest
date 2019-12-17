package com.github.corese4rch.cvurl.easytest.domain.asserts.property;

import com.github.corese4rch.cvurl.easytest.domain.EasyRequest;
import com.github.corese4rch.cvurl.easytest.domain.asserts.rule.Rule;

import java.util.function.Predicate;

import static java.lang.String.format;

/**
 * RequestProperty is wrapper around Rule that puts it into context of some property of the request.
 *
 * @param <T>
 */
public abstract class RequestProperty<T> {
    public static final String DESCRIPTION_TEMPLATE = "%s should [%s]";

    public Rule<EasyRequest> is(Rule<T> propertyPredicate) {
        return is(propertyPredicate::isValid, propertyPredicate.getDescription());
    }

    public Rule<EasyRequest> is(Predicate<T> predicate, String description) {
        return Rule.of(getPredicate(predicate),
                format(DESCRIPTION_TEMPLATE, getName(), description));
    }

    public Rule<EasyRequest> isNot(Rule<T> propertyPredicate) {
        return isNot(propertyPredicate::isValid, propertyPredicate.getDescription());
    }

    public Rule<EasyRequest> isNot(Predicate<T> predicate, String description) {
        return is(predicate.negate(), description);
    }

    protected abstract String getName();

    protected abstract Predicate<EasyRequest> getPredicate(Predicate<T> propertyPredicate);
}
