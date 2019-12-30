package com.github.corese4rch.cvurl.easytest.asserts.property;

import com.github.corese4rch.cvurl.easytest.asserts.rule.Rule;
import com.github.corese4rch.cvurl.easytest.asserts.rule.Rules;
import com.github.corese4rch.cvurl.easytest.domain.EasyRequest;

import java.util.function.Predicate;

import static java.lang.String.format;

/**
 * RequestProperty is wrapper around Rule that puts it into context of some property of the request.
 *
 * @param <T>
 */
public abstract class RequestProperty<T> {
    public static final String DESCRIPTION_TEMPLATE = "%s should [%s]";

    public Rule<EasyRequest> is(Rule<T> propertyRule) {
        return Rules.of(propertyRule, this::mapProperty);
    }

    public Rule<EasyRequest> is(Predicate<T> predicate, String description) {
        return Rules.of(getPredicate(predicate), format(DESCRIPTION_TEMPLATE, getName(), description));
    }

    public Rule<EasyRequest> isNot(Rule<T> propertyRule) {
        return Rules.negate(Rules.of(propertyRule, this::mapProperty));
    }

    public Rule<EasyRequest> isNot(Predicate<T> predicate, String description) {
        return is(predicate.negate(), description);
    }

    private Predicate<EasyRequest> getPredicate(Predicate<T> propertyPredicate) {
        return request -> propertyPredicate.test(mapProperty(request));
    }

    protected abstract String getName();

    protected abstract T mapProperty(EasyRequest request);
}
