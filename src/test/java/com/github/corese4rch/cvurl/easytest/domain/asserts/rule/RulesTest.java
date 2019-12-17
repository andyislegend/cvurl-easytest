package com.github.corese4rch.cvurl.easytest.domain.asserts.rule;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RulesTest {

    @ParameterizedTest
    @CsvSource({
            "true, true, true",
            "true, false, false",
            "false, true, false",
            "false, false, false"})
    public void andTest(Boolean rule1Result, Boolean rule2Result, Boolean result) {
        //given
        var rule1 = Rule.of(it -> rule1Result, "rule1");
        var rule2 = Rule.of(it -> rule2Result, "rule2");

        //when
        var andRule = Rules.and(rule1, rule2);

        //then}
        assertThat(andRule.isValid(new Object())).isEqualTo(result);
        assertThat(andRule.getDescription()).isEqualTo("rule1 and rule2");
    }

    @ParameterizedTest
    @CsvSource({
            "true, true, true",
            "true, false, true",
            "false, true, true",
            "false, false, false"})
    public void orTest(Boolean rule1Result, Boolean rule2Result, Boolean result) {
        //given
        var rule1 = Rule.of(it -> rule1Result, "rule1");
        var rule2 = Rule.of(it -> rule2Result, "rule2");

        //when
        var orRule = Rules.or(rule1, rule2);

        //then}
        assertThat(orRule.isValid(new Object())).isEqualTo(result);
        assertThat(orRule.getDescription()).isEqualTo("rule1 or rule2");
    }

    @Test
    public void equalTest() {
        //when
        var equalRule = Rules.equal(10);

        //then
        assertThat(equalRule.isValid(10)).isTrue();
        assertThat(equalRule.isNotValid(11)).isTrue();
    }

    @Test
    public void notEqualTest() {
        //when
        var equalRule = Rules.notEqual(10);

        //then
        assertThat(equalRule.isValid(11)).isTrue();
        assertThat(equalRule.isNotValid(10)).isTrue();
    }

    @Test
    public void containsTest() {
        //when
        var containsRule = Rules.contains("Hello");

        //then
        assertThat(containsRule.isValid("Hello World"));
        assertThat(containsRule.isNotValid("World"));
    }

    @Test
    public void startsWithTest() {
        //when
        var startsWithRule = Rules.startsWith("Hello");

        //then
        assertThat(startsWithRule.isValid("Hello World"));
        assertThat(startsWithRule.isNotValid("World Hello"));
    }

    @Test
    public void endsWithTest() {
        //when
        var startsWithRule = Rules.endsWith("World");

        //then
        assertThat(startsWithRule.isValid("Hello World"));
        assertThat(startsWithRule.isNotValid("World Hello"));
    }

    @Test
    public void containsKeyTest() {
        //when
        var containsKeyRule = Rules.containsKey("key");

        //then
        assertThat(containsKeyRule.isValid(Map.of("key", "value"))).isTrue();
        assertThat(containsKeyRule.isNotValid(Map.of("key2", "value"))).isTrue();
    }

    @Test
    public void containsKeyWithValueTest() {
        //when
        var containsKeyRule = Rules.containsKeyWithValue("key", "value");

        //then
        assertThat(containsKeyRule.isValid(Map.of("key", "value"))).isTrue();
        assertThat(containsKeyRule.isNotValid(Map.of("key2", "value"))).isTrue();
        assertThat(containsKeyRule.isNotValid(Map.of("key", "value2"))).isTrue();
    }

    @Test
    public void eqTest() {
        //when
        var eqRule = Rules.eq(1);

        //then
        assertThat(eqRule.isValid(1)).isTrue();
        assertThat(eqRule.isNotValid(2)).isTrue();
    }

    @Test
    public void gtTest() {
        //when
        var gtRule = Rules.gt(1);

        //then
        assertThat(gtRule.isValid(2)).isTrue();
        assertThat(gtRule.isNotValid(1)).isTrue();
    }

    @Test
    public void gteTest() {
        //when
        var gteRule = Rules.gte(1);

        //then
        assertThat(gteRule.isValid(1)).isTrue();
        assertThat(gteRule.isValid(2)).isTrue();
        assertThat(gteRule.isNotValid(0)).isTrue();
    }

    @Test
    public void ltTest() {
        //when
        var ltRule = Rules.lt(1);

        //then
        assertThat(ltRule.isValid(0)).isTrue();
        assertThat(ltRule.isNotValid(1)).isTrue();
    }

    @Test
    public void lteTest() {
        //when
        var lteRule = Rules.lte(1);

        //then
        assertThat(lteRule.isValid(1)).isTrue();
        assertThat(lteRule.isValid(0)).isTrue();
        assertThat(lteRule.isNotValid(2)).isTrue();
    }

    @Test
    public void noneTest() {
        //when
        var noneRule = Rules.none();

        //then
        assertThat(noneRule.isValid(0));
        assertThat(noneRule.isNotValid(1));
    }

}