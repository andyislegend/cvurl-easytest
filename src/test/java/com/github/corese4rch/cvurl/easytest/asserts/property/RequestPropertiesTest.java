package com.github.corese4rch.cvurl.easytest.asserts.property;

import com.github.corese4rch.cvurl.easytest.asserts.rule.Rule;
import com.github.corese4rch.cvurl.easytest.asserts.rule.Rules;
import com.github.corese4rch.cvurl.easytest.domain.EasyCVurl;
import com.github.corese4rch.cvurl.easytest.utils.User;
import coresearch.cvurl.io.constant.HttpMethod;
import coresearch.cvurl.io.internal.configuration.RequestConfiguration;
import coresearch.cvurl.io.mapper.BodyType;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Map;

import static com.github.corese4rch.cvurl.easytest.asserts.CVurlAssert.assertThat;
import static com.github.corese4rch.cvurl.easytest.asserts.property.RequestProperties.*;
import static com.github.corese4rch.cvurl.easytest.asserts.rule.Rules.*;
import static com.github.corese4rch.cvurl.easytest.utils.Urls.TEST_URL;
import static com.github.corese4rch.cvurl.easytest.utils.User.hasName;

public class RequestPropertiesTest {

    private EasyCVurl easyCVurl = new EasyCVurl();

    @Test
    public void bodyTest() {
        //given
        String body = "body";

        //when
        easyCVurl.post(TEST_URL)
                .body(body);

        //then
        assertThat(easyCVurl).hasRequests(eq(1), and(
                body().is(equal(body)),
                body().isNot(equal("not body")),
                body().is(b -> b.equals(body), "equal to " + body),
                body().isNot(b -> b.equals("not body"), "equal to " + body)));
    }

    @Test
    public void bodyWithMapperTest() {
        //given
        String body = "body";

        //when
        easyCVurl.post(TEST_URL)
                .body(body);

        //then
        assertThat(easyCVurl).hasRequests(eq(1), and(
                body(User::new).is(hasName(body)),
                body(User::new).isNot(hasName("notBody")),
                body(User::new).is(user -> user.getName().equals(body), "name equal to " + body),
                body(User::new).isNot(user -> user.getName().equals("notBody"), "name equal to notBody")));
    }

    @Test
    public void bodyWithClassTest() {
        //given
        String username = "username";
        String body = asJson(new User(username));

        //when
        easyCVurl.post(TEST_URL)
                .body(body);

        //then
        assertThat(easyCVurl).hasRequests(eq(1), and(
                body(User.class).is(hasName(username)),
                body(User.class).isNot(hasName("notUsername")),
                body(User.class).is(user -> user.getName().equals(username), "name equal to" + username),
                body(User.class).isNot(user -> user.getName().equals("not username"), "name equal to notUsername")));
    }

    private String asJson(Object object) {
        return easyCVurl.getConfiguration().getGenericMapper().writeValue(object);
    }

    @Test
    public void bodyWithBodyTypeTest() {
        //given
        String username = "username";
        String body = asJson(new User(username));
        BodyType<User> userBodyType = new BodyType<>() {
        };

        //when
        easyCVurl.post(TEST_URL)
                .body(body);

        //then
        assertThat(easyCVurl).hasRequests(eq(1), and(
                body(userBodyType).is(hasName(username)),
                body(userBodyType).isNot(hasName("notUsername")),
                body(userBodyType).is(user -> user.getName().equals(username), "name equal to " + username),
                body(userBodyType).isNot(user -> user.getName().equals("notUsername"), "name equal to notUsername")));
    }

    @Test
    public void configurationTest() {
        //given
        Duration timeout = Duration.ofSeconds(1);
        Duration wrongTimeout = Duration.ofSeconds(2);

        //when
        easyCVurl.get(TEST_URL)
                .requestTimeout(timeout);

        //then
        assertThat(easyCVurl).hasRequests(eq(1), and(
                configuration().is(hasTimeout(timeout)),
                configuration().isNot(hasTimeout(wrongTimeout)),
                configuration().is(conf -> conf.getRequestTimeout().orElseThrow().equals(timeout),
                        "request timeout should be " + timeout),
                configuration().isNot(conf -> conf.getRequestTimeout()
                                .orElseThrow().equals(wrongTimeout),
                        "request timeout should be " + wrongTimeout)));
    }

    @Test
    public void headersTest() {
        //given
        Map<String, String> headers = Map.of("header", "headerVal");

        //when
        easyCVurl.get(TEST_URL)
                .headers(headers);

        //then
        assertThat(easyCVurl).hasRequests(eq(1), and(
                headers().is(containsKey("header")),
                headers().isNot(containsKey("notHeader")),
                headers().is(h -> h.get("header") != null, "contain header with name header"),
                headers().isNot(h -> h.get("notHeader") != null, "contain header with name notHeader")
        ));
    }

    @Test
    public void paramsTest() {
        //given
        Map<String, String> params = Map.of("param", "paramVal");

        //when
        easyCVurl.get(TEST_URL)
                .queryParams(params);

        //then
        assertThat(easyCVurl).hasRequests(eq(1), and(
                params().is(containsKey("param")),
                params().isNot(containsKey("notParam")),
                params().is(h -> h.get("param") != null, "contain param with name param"),
                params().isNot(h -> h.get("notParam") != null, "contain param with name notParam")
        ));
    }

    @Test
    public void methodTest() {
        //when
        easyCVurl.get(TEST_URL);

        //then
        assertThat(easyCVurl).hasRequests(eq(1), and(
                method().is(equal(HttpMethod.GET)),
                method().isNot(equal(HttpMethod.POST)),
                method().is(m -> m == HttpMethod.GET, "should be GET"),
                method().isNot(m -> m == HttpMethod.POST, "should be POST")
        ));
    }

    @Test
    public void urlTest() {
        //when
        easyCVurl.get(TEST_URL);

        //then
        assertThat(easyCVurl).hasRequests(eq(1), and(
                url().is(equal(TEST_URL)),
                url().isNot(equal("badUrl")),
                url().is(url -> url.equals(TEST_URL), "should be " + TEST_URL),
                url().isNot(url -> url.equals("badUrl"), "should be badUrl")
        ));
    }

    public Rule<RequestConfiguration> hasTimeout(Duration timeout) {
        return Rules.of(conf -> conf.getRequestTimeout().orElseThrow().equals(timeout),
                "request timeout should be " + timeout);
    }
}