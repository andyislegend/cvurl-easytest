package com.github.corese4rch.cvurl.easytest.domain;

import coresearch.cvurl.io.mapper.BodyType;
import coresearch.cvurl.io.model.Configuration;
import coresearch.cvurl.io.multipart.MultipartBody;
import coresearch.cvurl.io.request.Request;
import coresearch.cvurl.io.request.RequestWithBodyBuilder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.function.Function;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockRequestBuilderFactory {

    @SuppressWarnings("unchecked")
    static RequestWithBodyBuilder createMockRequestWithBodyBuilder(EasyRequest request, Configuration configuration, Request mockRequest) {
        RequestWithBodyBuilder builder = mock(RequestWithBodyBuilder.class);

        setupRequestBuilding(request, configuration, builder);
        setupRequestSending(request, mockRequest, builder);

        when(builder.create()).thenReturn(builder);
        return builder;
    }

    private static void setupRequestBuilding(EasyRequest request, Configuration configuration, RequestWithBodyBuilder builder) {
        when(builder.header(any(), any())).thenAnswer(inv -> {
            request.addHeader(inv.getArgument(0), inv.getArgument(1));
            return builder;
        });

        when(builder.headers(any())).thenAnswer(inv -> {
            request.setHeaders(inv.getArgument(0));
            return builder;
        });

        when(builder.queryParam(any(), any())).thenAnswer(inv -> {
            request.addParam(inv.getArgument(0), inv.getArgument(1));
            return builder;
        });

        when(builder.queryParams(any())).thenAnswer(inv -> {
            request.setParams(inv.getArgument(0));
            return builder;
        });

        when(builder.timeout(any())).thenAnswer(inv -> {
            request.getConfiguration().setRequestTimeout(inv.getArgument(0));
            return builder;
        });

        when(builder.requestTimeout(any())).thenAnswer(inv -> {
            request.getConfiguration().setRequestTimeout(inv.getArgument(0));
            return builder;
        });

        when(builder.acceptCompressed(anyBoolean())).thenAnswer(inv -> {
            request.getConfiguration().setAcceptCompressed(inv.getArgument(0));
            return builder;
        });

        when(builder.logEnabled(anyBoolean())).thenAnswer(inv -> {
            request.getConfiguration().setLogEnabled(inv.getArgument(0));
            return builder;
        });

        when(builder.body(anyString())).thenAnswer(inv -> {
            request.setBody(inv.getArgument(0));
            return builder;
        });

        when(builder.body(any(byte[].class))).thenAnswer(inv -> {
            request.setBody(new String(inv.getArgument(0, byte[].class)));
            return builder;
        });

        when(builder.body(any(Object.class))).thenAnswer(inv -> {
            request.setBody(configuration.getGenericMapper().writeValue(inv.getArgument(0)));
            return builder;
        });

        when(builder.body(any(MultipartBody.class))).thenAnswer(inv -> {
            String body = inv.getArgument(0, MultipartBody.class).asByteArrays().stream()
                    .map(String::new)
                    .collect(Collectors.joining());

            request.setBody(body);
            return builder;
        });
    }

    @SuppressWarnings("unchecked")
    private static void setupRequestSending(EasyRequest request, Request mockRequest, RequestWithBodyBuilder builder) {
        when(builder.asyncAsObject(any(Class.class), anyInt())).thenAnswer(execAnswer(request, inv ->
                mockRequest.asyncAsObject(inv.getArgument(0, Class.class), inv.getArgument(1))
        ));
        when(builder.asyncAsObject(any(BodyType.class), anyInt())).thenAnswer(execAnswer(request, inv ->
                mockRequest.asyncAsObject(inv.getArgument(0, BodyType.class), inv.getArgument(1))
        ));
        when(builder.asyncAsObject(any(Class.class))).thenAnswer(execAnswer(request, inv ->
                mockRequest.asyncAsObject(inv.getArgument(0, Class.class))
        ));
        when(builder.asyncAsObject(any(BodyType.class))).thenAnswer(execAnswer(request, inv ->
                mockRequest.asyncAsObject(inv.getArgument(0, BodyType.class))
        ));
        when(builder.asyncAsString()).thenAnswer(execAnswer(request, inv ->
                mockRequest.asyncAsString()
        ));
        when(builder.asyncAsString(any())).thenAnswer(execAnswer(request, inv ->
                mockRequest.asyncAsString(inv.getArgument(0))
        ));
        when(builder.asyncAsStream()).thenAnswer(execAnswer(request, inv ->
                mockRequest.asyncAsStream()
        ));
        when(builder.asyncAsStream(any())).thenAnswer(execAnswer(request, inv ->
                mockRequest.asyncAsStream(inv.getArgument(0))
        ));
        when(builder.asyncAs(any())).thenAnswer(execAnswer(request, inv ->
                mockRequest.asyncAs(inv.getArgument(0))
        ));
        when(builder.asyncAs(any(), any())).thenAnswer(execAnswer(request, inv ->
                mockRequest.asyncAs(inv.getArgument(0), inv.getArgument(1))
        ));
        when(builder.asObject(any(Class.class), anyInt())).thenAnswer(execAnswer(request, inv ->
                mockRequest.asObject(inv.getArgument(0, Class.class), inv.getArgument(1))
        ));
        when(builder.asObject(any(BodyType.class), anyInt())).thenAnswer(execAnswer(request, inv ->
                mockRequest.asObject(inv.getArgument(0, BodyType.class), inv.getArgument(1))
        ));
        when(builder.asObject(any(Class.class))).thenAnswer(execAnswer(request, inv ->
                mockRequest.asObject(inv.getArgument(0, Class.class))
        ));
        when(builder.asObject(any(BodyType.class))).thenAnswer(execAnswer(request, inv ->
                mockRequest.asObject(inv.getArgument(0, BodyType.class))
        ));
        when(builder.asString()).thenAnswer(execAnswer(request, inv ->
                mockRequest.asString()
        ));
        when(builder.asStream()).thenAnswer(execAnswer(request, inv ->
                mockRequest.asStream()
        ));
        when(builder.as(any())).thenAnswer(execAnswer(request, inv ->
                mockRequest.as(inv.getArgument(0))
        ));
    }

    private static <T> Answer<?> execAnswer(EasyRequest request, Function<InvocationOnMock, T> supplier) {
        return inv -> {
            request.incrementExecuted();
            return supplier.apply(inv);
        };
    }

}
