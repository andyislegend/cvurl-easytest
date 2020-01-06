package com.github.corese4rch.cvurl.easytest;

import com.github.corese4rch.cvurl.easytest.asserts.rule.Rule;
import com.github.corese4rch.cvurl.easytest.asserts.rule.Rules;
import com.github.corese4rch.cvurl.easytest.domain.EasyCVurl;
import com.github.corese4rch.cvurl.easytest.utils.User;
import coresearch.cvurl.io.constant.HttpMethod;
import coresearch.cvurl.io.internal.configuration.RequestConfiguration;
import coresearch.cvurl.io.mapper.BodyType;
import coresearch.cvurl.io.mapper.GenericMapper;
import coresearch.cvurl.io.model.Configuration;
import coresearch.cvurl.io.multipart.MultipartBody;
import coresearch.cvurl.io.multipart.Part;
import coresearch.cvurl.io.request.Request;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

import static com.github.corese4rch.cvurl.easytest.asserts.CVurlAssert.assertThat;
import static com.github.corese4rch.cvurl.easytest.asserts.property.RequestProperties.*;
import static com.github.corese4rch.cvurl.easytest.asserts.rule.Rules.*;
import static com.github.corese4rch.cvurl.easytest.utils.Urls.TEST_URL;
import static com.github.corese4rch.cvurl.easytest.utils.User.hasName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class EasyCVurlTest {

    private EasyCVurl cVurl = new EasyCVurl();

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
                configuration().is(hasTimeout(timeout)),
                configuration().is(RequestConfiguration::isAcceptCompressed,
                        "should accept compressed"),
                configuration().is(RequestConfiguration::isLogEnabled,
                        "log should be enabled")));
    }

    @Test
    public void requestBuildingWithHeaderTest() {
        //when
        cVurl.get(TEST_URL)
                .header("header", "headerVal")
                .asString();

        //then
        assertThat(cVurl).hasRequests(eq(1),
                headers().is(containsKeyWithValue("header", "headerVal")));
    }

    @Test
    public void requestBuildingWithParamTest() {
        //when
        cVurl.get(TEST_URL)
                .queryParam("param", "paramVal")
                .asString();

        //then
        assertThat(cVurl).hasRequests(eq(1),
                params().is(containsKeyWithValue("param", "paramVal")));
    }

    @Test
    public void requestBuildingWithTimeoutTest() {
        //given
        Duration timeout = Duration.ofSeconds(1);

        //when
        cVurl.get(TEST_URL)
                .timeout(timeout)
                .asString();

        //then
        assertThat(cVurl).hasRequests(eq(1),
                configuration().is(hasTimeout(timeout)));
    }

    @Test
    public void requestBuildingWithStringBodyTest() {
        //given
        String body = "body";

        //when
        cVurl.post(TEST_URL)
                .body(body)
                .asString();

        //then
        assertThat(cVurl).hasRequests(eq(1), body().is(equal(body)));
    }

    @Test
    public void requestBuildingWithBytesBodyTest() {
        //given
        String body = "body";

        //when
        cVurl.post(TEST_URL)
                .body(body.getBytes())
                .asString();

        //then
        assertThat(cVurl).hasRequests(eq(1), body().is(equal(body)));
    }

    @Test
    public void requestBuildingWithObjectBodyTest() {
        //given
        User body = new User("username");

        //when
        cVurl.post(TEST_URL)
                .body(body)
                .asString();

        //then
        assertThat(cVurl).hasRequests(eq(1),
                body(User.class).is(hasName(body.getName())));
    }

    @Test
    public void requestBuildingWithMultipartBodyTest() {
        //given
        MultipartBody body = MultipartBody.create("sep").part(Part.of("body"));


        //when
        cVurl.post(TEST_URL)
                .body(body)
                .asString();

        //then
        assertThat(cVurl).hasRequests(eq(1),
                body().is(equal("--sep\r\n\r\nbody\r\n--sep--")));
    }

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
    public void cVurlWithPredefinedConfigurationTest() {
        //given
        Configuration configuration = Configuration.builder()
                .genericMapper(createTestGenericMapper("test"))
                .build();

        //when
        EasyCVurl easyCVurl = new EasyCVurl(configuration);

        //then
        assertThat(easyCVurl.getConfiguration().getGenericMapper().writeValue(new Object())).isEqualTo("test");
    }

    private Rule<RequestConfiguration> hasTimeout(Duration timeout) {
        return Rules.of(conf -> conf.getRequestTimeout().orElseThrow().equals(timeout),
                "request timeout should be " + timeout);
    }

    private GenericMapper createTestGenericMapper(String writeReturnValue) {
        return new GenericMapper() {
            @Override
            public <T> T readValue(String s, Class<T> aClass) {
                return null;
            }

            @Override
            public <T> T readValue(String s, BodyType<T> bodyType) {
                return null;
            }

            @Override
            public String writeValue(Object o) {
                return writeReturnValue;
            }
        };
    }
}