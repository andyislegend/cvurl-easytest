package com.github.corese4rch.cvurl.easytest.domain;

import com.github.corese4rch.cvurl.easytest.domain.utils.User;
import coresearch.cvurl.io.constant.HttpMethod;
import coresearch.cvurl.io.internal.configuration.RequestConfiguration;
import coresearch.cvurl.io.request.Request;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

import static com.github.corese4rch.cvurl.easytest.domain.asserts.CVurlAssert.assertThat;
import static com.github.corese4rch.cvurl.easytest.domain.asserts.property.RequestProperties.*;
import static com.github.corese4rch.cvurl.easytest.domain.asserts.rule.Rules.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class EasyCVurlTest {

    private static final String TEST_URL = "http://test.com";

    private EasyCVurl cVurl = new EasyCVurl();

    @Test
    public void cVurlWithPredefinedResponseTest() {
        //given
        var user = new User("Test");
        var mockRequest = Mockito.mock(Request.class);
        when(mockRequest.asObject(User.class)).thenReturn(user);

        //when
        var resultUser = new EasyCVurl(mockRequest).get(TEST_URL).asObject(User.class);

        //then
        assertThat(resultUser).isEqualTo(user);
    }

    @Test
    public void getTest() throws MalformedURLException {
        //when
        cVurl.get(TEST_URL);
        cVurl.get(new URL(TEST_URL));

        //then
        assertThat(cVurl).hasRequests(eq(2), and(
                url().is(equal(TEST_URL)),
                method().is(equal(HttpMethod.GET))
        ));
    }

    @Test
    public void postTest() throws MalformedURLException {
        //when
        cVurl.post(TEST_URL);
        cVurl.post(new URL(TEST_URL));

        //then
        assertThat(cVurl).hasRequests(eq(2), and(
                url().is(equal(TEST_URL)),
                method().is(equal(HttpMethod.POST))
        ));
    }

    @Test
    public void putTest() throws MalformedURLException {
        //when
        cVurl.put(TEST_URL);
        cVurl.put(new URL(TEST_URL));

        //then
        assertThat(cVurl).hasRequests(eq(2), and(
                url().is(equal(TEST_URL)),
                method().is(equal(HttpMethod.PUT))
        ));
    }

    @Test
    public void deleteTest() throws MalformedURLException {
        //when
        cVurl.delete(TEST_URL);
        cVurl.delete(new URL(TEST_URL));

        //then
        assertThat(cVurl).hasRequests(eq(2), and(
                url().is(equal(TEST_URL)),
                method().is(equal(HttpMethod.DELETE))
        ));
    }

    @Test
    public void patchTest() throws MalformedURLException {
        //when
        cVurl.patch(TEST_URL);
        cVurl.patch(new URL(TEST_URL));

        //then
        assertThat(cVurl).hasRequests(eq(2), and(
                url().is(equal(TEST_URL)),
                method().is(equal(HttpMethod.PATCH))
        ));
    }

    @Test
    public void requestBuildingTest() {
        //given
        Duration timeout = Duration.ofSeconds(1);

        //when
        cVurl.get(TEST_URL)
                .headers(Map.of("header", "headerVal"))
                .queryParams(Map.of("param", "paramVal"))
                .requestTimeout(timeout)
                .acceptCompressed(true)
                .logEnabled(true)
                .asString();

        //then
        assertThat(cVurl).hasRequests(eq(1), and(
                headers().is(containsKeyWithValue("header", "headerVal")),
                params().is(containsKeyWithValue("param", "paramVal")),
                configuration().is(
                        conf -> conf.getRequestTimeout().orElseThrow().equals(timeout),
                        "request timeout should be " + timeout),
                configuration().is(RequestConfiguration::isAcceptCompressed,
                        "should accept compressed"),
                configuration().is(RequestConfiguration::isLogEnabled,
                        "log should be enabled")));
    }
}