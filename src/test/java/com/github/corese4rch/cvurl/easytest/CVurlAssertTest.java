package com.github.corese4rch.cvurl.easytest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.corese4rch.cvurl.easytest.domain.EasyCVurl;
import com.github.corese4rch.cvurl.easytest.domain.EasyRequest;
import com.github.corese4rch.cvurl.easytest.utils.Urls;
import com.github.corese4rch.cvurl.easytest.utils.User;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static com.github.corese4rch.cvurl.easytest.asserts.CVurlAssert.assertThat;
import static com.github.corese4rch.cvurl.easytest.asserts.property.RequestProperties.body;
import static com.github.corese4rch.cvurl.easytest.asserts.property.RequestProperties.params;
import static com.github.corese4rch.cvurl.easytest.asserts.rule.Rules.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CVurlAssertTest {

    private EasyCVurl cVurl = new EasyCVurl();

    @Test
    public void hasNoRequestsTest() {
        assertThat(cVurl).hasRequests(none());
    }

    @Test
    public void hasNoRequestsFailTest() {
        //when
        cVurl.get(Urls.TEST_URL).asString();

        //then
        assertThrows(AssertionError.class, () -> assertThat(cVurl).hasRequests(none()));
    }

    @Test
    public void simpleHasRequestsTest() {
        //when
        cVurl.get(Urls.TEST_URL).asString();
        cVurl.get(Urls.TEST_URL).asString();

        //then
        assertThat(cVurl).hasRequests(eq(2));
    }

    @Test
    public void simpleHasRequestsFailedTest() {
        //when
        cVurl.get(Urls.TEST_URL).asString();
        cVurl.get(Urls.TEST_URL).asString();

        //then
        assertThrows(AssertionError.class, () -> assertThat(cVurl).hasRequests(eq(3)));
    }

    @Test
    public void hasRequestsWithRuleTest() {
        //given
        User user = new User("Test");

        //when
        cVurl.post(Urls.TEST_URL).body(user).asString();

        //then
        assertThat(cVurl).hasRequests(eq(1), body(User.class).is(User.hasName("Test")));
    }

    @Test
    public void hasRequestsWithRuleFailedTest() {
        //given
        User user = new User("Test");

        //when
        cVurl.post(Urls.TEST_URL).body(user).asString();

        //then
        assertThrows(AssertionError.class, () ->
                assertThat(cVurl).hasRequests(eq(1), body(User.class).is(User.hasName("NotTest"))));
    }

    @Test
    public void hasExecutedRequestsTest() {
        //when
        cVurl.get(Urls.TEST_URL).asString();
        cVurl.get(Urls.TEST_URL);

        //then
        assertThat(cVurl).hasExecutedRequests(eq(1));
    }

    @Test
    public void hasExecutedRequestsWithRuleTest() {
        //when
        cVurl.get(Urls.TEST_URL).queryParam("key", "value").asString();
        cVurl.get(Urls.TEST_URL);

        //then
        assertThat(cVurl).hasExecutedRequests(eq(1), params().is(containsKey("key")));
    }

    private EasyRequest getEasyRequest(String body) {
        try {
            return new ObjectMapper().readValue(body, EasyRequest.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    public Predicate<EasyRequest> predicate() {
        return r -> r.getTimesExecuted() > 3;
    }
}