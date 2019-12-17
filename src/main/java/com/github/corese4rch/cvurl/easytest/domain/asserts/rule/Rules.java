package com.github.corese4rch.cvurl.easytest.domain.asserts.rule;

import java.util.List;
import java.util.Map;

import static com.github.corese4rch.cvurl.easytest.domain.asserts.rule.Rule.of;
import static java.lang.String.format;

public class Rules {

    @SafeVarargs
    public static <T> Rule<T> and(Rule<T>... rules) {
        return new AndRule<>(List.of(rules));
    }

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

    public static Rule<Map<String, String>> containsKey(String key) {
        return of(actual -> actual.containsKey(key), "contains key " + key);
    }

    public static Rule<Map<String, String>> containsKeyWithValue(String key, String value) {
        return of(actual -> {
            String actualValue = actual.get(key);
            return actualValue != null && actualValue.equals(actualValue);
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
