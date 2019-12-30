package com.github.corese4rch.cvurl.easytest.asserts.rule;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.lang.String.format;

public class Rules {

    public static <T> Rule<T> of(Predicate<T> predicate, String description) {
        return new RuleImpl<>(predicate, description);
    }

    public static <T, R> Rule<T> of(Rule<R> rule, Function<T, R> objMapper) {
        return new DelegatorRule<>(rule, objMapper);
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
        return of(actual -> actual.equals(expected), "equals to " + expected);
    }

    public static <T> Rule<T> notEqual(T expected) {
        return of(actual -> !actual.equals(expected), "not equals to " + expected);
    }

    public static Rule<String> contains(String expected) {
        return of(actual -> actual.contains(expected), "contains " + expected);
    }

    public static Rule<String> startsWith(String expected) {
        return of(actual -> actual.startsWith(expected), "starts with " + expected);
    }

    public static Rule<String> endsWith(String expected) {
        return of(actual -> actual.endsWith(expected), "ends with " + expected);
    }

    public static Rule<String> matches(String regex) {
        return of(actual -> actual.matches(regex), "matches " + regex);
    }

    public static Rule<Map<String, String>> containsKey(String key) {
        return of(actual -> actual.containsKey(key), "contains key " + key);
    }

    public static Rule<Map<String, String>> containsKeyWithValue(String key, String value) {
        return of(actual -> {
            String actualValue = actual.get(key);
            return actualValue != null && actualValue.equals(value);
        }, format("contains key %s with value %s", key, value));
    }

    public static Rule<Integer> eq(Integer expectedNum) {
        return of(actualNum -> actualNum.equals(expectedNum), "equal to " + expectedNum);
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
        return of(actualNum -> actualNum == 0, "none");
    }
}
