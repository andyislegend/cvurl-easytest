package com.github.corese4rch.cvurl.easytest.asserts.rule;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.lang.String.format;

public class Rules {

    public static <T> Rule<T> of(Predicate<T> predicate, String description) {
        return new RuleImpl<>(predicate, description);
    }

    public static <T, R> Rule<T> of(Rule<R> rule, Function<T, R> objMapper, String descriptionPrefix) {
        return new DelegatorRule<>(rule, objMapper, descriptionPrefix);
    }

    public static <T> Rule<T> negate(Rule<T> rule) {
        return new NegatedRule<>(rule);
    }

    @SafeVarargs
    public static <T> Rule<T> and(Rule<T>... rules) {
        return new AndRule<>(List.of(rules));
    }

    @SafeVarargs
    public static <T> Rule<T> or(Rule<T>... rules) {
        return new OrRule<>(List.of(rules));
    }

    public static <T> Rule<T> equal(T expected) {
        return of(actual -> Objects.equals(actual, expected), "equals to " + expected);
    }

    public static <T> Rule<T> notEqual(T expected) {
        return of(actual -> !Objects.equals(actual, expected), "not equals to " + expected);
    }

    public static Rule<String> contains(String expected) {
        return of(actual -> actual != null && actual.contains(expected), "contains " + expected);
    }

    public static Rule<String> startsWith(String expected) {
        return of(actual -> actual != null && actual.startsWith(expected), "starts with " + expected);
    }

    public static Rule<String> endsWith(String expected) {
        return of(actual -> actual != null && actual.endsWith(expected), "ends with " + expected);
    }

    public static Rule<String> matches(String regex) {
        return of(actual -> actual != null && actual.matches(regex), "matches " + regex);
    }

    public static Rule<Map<String, String>> containsKey(String key) {
        return of(actual -> actual != null && actual.containsKey(key), "contains key " + key);
    }

    public static Rule<Map<String, String>> containsKeyWithValue(String key, String value) {
        return of(actual -> {
            String actualValue = actual != null ? actual.get(key) : null;
            return actualValue != null && actualValue.equals(value);
        }, format("contains key %s with value %s", key, value));
    }

    public static Rule<Integer> eq(Integer expectedNum) {
        return of(actualNum -> Objects.equals(actualNum, expectedNum), "equal to " + expectedNum);
    }

    public static Rule<Integer> gt(Integer expectedNum) {
        return of(actualNum -> actualNum > expectedNum, "greater than " + expectedNum);
    }

    public static Rule<Integer> gte(Integer expectedNum) {
        return of(actualNum -> actualNum >= expectedNum, "greater than or equal to " + expectedNum);
    }

    public static Rule<Integer> lt(Integer expectedNum) {
        return of(actualNum -> actualNum < expectedNum, "less than " + expectedNum);
    }

    public static Rule<Integer> lte(Integer expectedNum) {
        return of(actualNum -> actualNum <= expectedNum, "less than or equal to " + expectedNum);
    }

    public static Rule<Integer> none() {
        return of(actualNum -> Objects.equals(actualNum, 0), "none");
    }
}
