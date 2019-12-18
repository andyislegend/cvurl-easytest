package com.github.corese4rch.cvurl.easytest.domain;

import com.github.corese4rch.cvurl.easytest.domain.utils.User;
import coresearch.cvurl.io.mapper.BodyType;
import coresearch.cvurl.io.model.Response;
import coresearch.cvurl.io.request.Request;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.github.corese4rch.cvurl.easytest.domain.utils.Urls.TEST_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CVurlWithPredefinedResponseTest {
    private Request request = mock(Request.class);

    @Test
    public void asyncAsObjectWithClassTest() throws ExecutionException, InterruptedException {
        //given
        User user = new User("username");
        when(request.asyncAsObject(User.class)).thenReturn(CompletableFuture.completedFuture(user));
        EasyCVurl cVurl = new EasyCVurl(request);

        //when
        CompletableFuture<User> future = cVurl.get(TEST_URL).asyncAsObject(User.class);

        //then
        assertThat(future.get()).isEqualTo(user);
    }

    @Test
    public void asyncAsObjectWithClassAndStatusCodeTest() throws ExecutionException, InterruptedException {
        //given
        User user = new User("username");
        when(request.asyncAsObject(User.class, 200)).thenReturn(CompletableFuture.completedFuture(user));
        EasyCVurl cVurl = new EasyCVurl(request);

        //when
        CompletableFuture<User> future = cVurl.get(TEST_URL).asyncAsObject(User.class, 200);

        //then
        assertThat(future.get()).isEqualTo(user);
    }


    @Test
    public void asyncAsObjectWithTypeTest() throws ExecutionException, InterruptedException {
        //given
        User user = new User("username");
        BodyType<User> bodyType = new BodyType<>() {
        };
        when(request.asyncAsObject(bodyType)).thenReturn(CompletableFuture.completedFuture(user));

        EasyCVurl cVurl = new EasyCVurl(request);

        //when
        CompletableFuture<User> future = cVurl.get(TEST_URL).asyncAsObject(bodyType);

        //then
        assertThat(future.get()).isEqualTo(user);
    }

    @Test
    public void asyncAsObjectWithTypeAndStatusCodeTest() throws ExecutionException, InterruptedException {
        //given
        User user = new User("username");
        BodyType<User> bodyType = new BodyType<>() {
        };
        when(request.asyncAsObject(bodyType, 200)).thenReturn(CompletableFuture.completedFuture(user));

        EasyCVurl cVurl = new EasyCVurl(request);

        //when
        CompletableFuture<User> future = cVurl.get(TEST_URL).asyncAsObject(bodyType, 200);

        //then
        assertThat(future.get()).isEqualTo(user);
    }

    @Test
    public void asyncAsStringTest() throws ExecutionException, InterruptedException {
        //given
        String body = "body";
        Response<String> mockResponse = mockResponse(body);
        when(request.asyncAsString()).thenReturn(CompletableFuture.completedFuture(mockResponse));
        EasyCVurl cVurl = new EasyCVurl(request);

        //when
        CompletableFuture<Response<String>> future = cVurl.get(TEST_URL).asyncAsString();

        //then
        assertThat(future.get().getBody()).isEqualTo(body);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void asyncAsStringWithPPHTest() throws ExecutionException, InterruptedException {
        //given
        String body = "body";
        Response<String> mockResponse = mockResponse(body);
        when(request.asyncAsString(any(HttpResponse.PushPromiseHandler.class)))
                .thenReturn(CompletableFuture.completedFuture(mockResponse));
        EasyCVurl cVurl = new EasyCVurl(request);

        //when
        CompletableFuture<Response<String>> future = cVurl.get(TEST_URL)
                .asyncAsString(mock(HttpResponse.PushPromiseHandler.class));

        //then
        assertThat(future.get().getBody()).isEqualTo(body);
    }

    @Test
    public void asyncAsStreamTest() throws ExecutionException, InterruptedException {
        //given
        String body = "body";
        Response<InputStream> mockResponse = mockResponse(new ByteArrayInputStream(body.getBytes()));
        when(request.asyncAsStream()).thenReturn(CompletableFuture.completedFuture(mockResponse));
        EasyCVurl cVurl = new EasyCVurl(request);

        //when
        CompletableFuture<Response<InputStream>> future = cVurl.get(TEST_URL).asyncAsStream();

        //then
        assertThat(future.get().getBody()).matches(is -> inputStreamEquals(is, body));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void asyncAsStreamWithPPHTest() throws ExecutionException, InterruptedException {
        //given
        String body = "body";
        Response<InputStream> mockResponse = mockResponse(new ByteArrayInputStream(body.getBytes()));
        when(request.asyncAsStream(any(HttpResponse.PushPromiseHandler.class)))
                .thenReturn(CompletableFuture.completedFuture(mockResponse));
        EasyCVurl cVurl = new EasyCVurl(request);

        //when
        CompletableFuture<Response<InputStream>> future = cVurl.get(TEST_URL)
                .asyncAsStream(mock(HttpResponse.PushPromiseHandler.class));

        //then
        assertThat(future.get().getBody()).matches(is -> inputStreamEquals(is, body));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void asyncAsTest() throws ExecutionException, InterruptedException {
        //given
        String body = "body";
        Response<String> mockResponse = mockResponse(body);
        when(request.asyncAs(any(HttpResponse.BodyHandler.class)))
                .thenReturn(CompletableFuture.completedFuture(mockResponse));
        EasyCVurl cVurl = new EasyCVurl(request);

        //when
        CompletableFuture<Response<String>> future = cVurl.get(TEST_URL)
                .asyncAs(HttpResponse.BodyHandlers.ofString());

        //then
        assertThat(future.get().getBody()).isEqualTo(body);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void asyncAsWithPPHTest() throws ExecutionException, InterruptedException {
        //given
        String body = "body";
        Response<String> mockResponse = mockResponse(body);
        when(request.asyncAs(any(HttpResponse.BodyHandler.class), any(HttpResponse.PushPromiseHandler.class)))
                .thenReturn(CompletableFuture.completedFuture(mockResponse));
        EasyCVurl cVurl = new EasyCVurl(request);

        //when
        CompletableFuture<Response<String>> future = cVurl.get(TEST_URL)
                .asyncAs(HttpResponse.BodyHandlers.ofString(), mock(HttpResponse.PushPromiseHandler.class));

        //then
        assertThat(future.get().getBody()).isEqualTo(body);
    }

    @Test
    public void asObjectWithClassTest() {
        //given
        User user = new User("username");
        when(request.asObject(User.class)).thenReturn(user);
        EasyCVurl cVurl = new EasyCVurl(request);

        //when
        User result = cVurl.get(TEST_URL).asObject(User.class);

        //then
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void asObjectWithClassAndStatusCodeTest() {
        //given
        User user = new User("username");
        when(request.asObject(User.class, 200)).thenReturn(Optional.of(user));
        EasyCVurl cVurl = new EasyCVurl(request);

        //when
        User result = cVurl.get(TEST_URL).asObject(User.class, 200)
                .orElseThrow(IllegalStateException::new);

        //then
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void asObjectWithTypeTest() {
        //given
        User user = new User("username");
        BodyType<User> bodyType = new BodyType<>() {
        };
        when(request.asObject(bodyType)).thenReturn(user);
        EasyCVurl cVurl = new EasyCVurl(request);

        //when
        User result = cVurl.get(TEST_URL).asObject(bodyType);

        //then
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void asObjectWithTypeAndStatusCodeTest() {
        //given
        User user = new User("username");
        BodyType<User> bodyType = new BodyType<>() {
        };
        when(request.asObject(bodyType, 200)).thenReturn(Optional.of(user));
        EasyCVurl cVurl = new EasyCVurl(request);

        //when
        User result = cVurl.get(TEST_URL).asObject(bodyType, 200)
                .orElseThrow(IllegalStateException::new);

        //then
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void asStringTest() {
        //given
        String body = "body";
        Response<String> mockResponse = mockResponse(body);
        when(request.asString()).thenReturn(Optional.of(mockResponse));
        EasyCVurl cVurl = new EasyCVurl(request);

        //when
        String result = cVurl.get(TEST_URL).asString().orElseThrow(IllegalStateException::new).getBody();

        //then
        assertThat(result).isEqualTo(body);
    }

    @Test
    public void asStreamTest() {
        //given
        String body = "body";
        Response<InputStream> mockResponse = mockResponse(new ByteArrayInputStream(body.getBytes()));
        when(request.asStream()).thenReturn(Optional.of(mockResponse));
        EasyCVurl cVurl = new EasyCVurl(request);

        //when
        InputStream result = cVurl.get(TEST_URL).asStream().orElseThrow(IllegalStateException::new).getBody();

        //then
        assertThat(result).matches(is -> inputStreamEquals(is, body));
    }

    @Test
    public void asTest() {
        //given
        String body = "body";
        Response<String> mockResponse = mockResponse(body);
        when(request.as(HttpResponse.BodyHandlers.ofString())).thenReturn(Optional.of(mockResponse));
        EasyCVurl cVurl = new EasyCVurl(request);

        //when
        String result = cVurl.get(TEST_URL).as(HttpResponse.BodyHandlers.ofString()).orElseThrow(IllegalStateException::new).getBody();

        //then
        assertThat(result).isEqualTo(body);
    }

    @SuppressWarnings("unchecked")
    private <T> Response<T> mockResponse(T body) {
        Response<T> response = (Response<T>) mock(Response.class);
        when(response.getBody()).thenReturn(body);
        return response;
    }

    private boolean inputStreamEquals(InputStream is, String str) {
        try {
            return new String(is.readAllBytes()).equals(str);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
