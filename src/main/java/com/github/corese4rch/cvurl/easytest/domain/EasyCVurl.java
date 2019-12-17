package com.github.corese4rch.cvurl.easytest.domain;

import coresearch.cvurl.io.constant.HttpMethod;
import coresearch.cvurl.io.request.CVurl;
import coresearch.cvurl.io.request.Request;
import coresearch.cvurl.io.request.RequestBuilder;
import coresearch.cvurl.io.request.RequestWithBodyBuilder;
import org.mockito.Mockito;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EasyCVurl extends CVurl {
    private List<EasyRequest> requests = new ArrayList<>();
    private Request mockRequest;

    public EasyCVurl(Request mockRequest) {
        this.mockRequest = mockRequest;
    }

    public EasyCVurl() {
        this.mockRequest = Mockito.mock(Request.class);
    }

    @Override
    public RequestBuilder<?> get(String url) {
        return MockRequestBuilderFactory
                .createMockRequestWithBodyBuilder(createRequest(url, HttpMethod.GET), getConfiguration(), mockRequest);
    }

    @Override
    public RequestBuilder<?> get(URL url) {
        return MockRequestBuilderFactory
                .createMockRequestWithBodyBuilder(createRequest(url.toString(), HttpMethod.GET), getConfiguration(), mockRequest);
    }

    @Override
    public RequestWithBodyBuilder post(String url) {
        return MockRequestBuilderFactory
                .createMockRequestWithBodyBuilder(createRequest(url, HttpMethod.POST), getConfiguration(), mockRequest);
    }

    @Override
    public RequestWithBodyBuilder post(URL url) {
        return MockRequestBuilderFactory
                .createMockRequestWithBodyBuilder(createRequest(url.toString(), HttpMethod.POST), getConfiguration(), mockRequest);
    }

    @Override
    public RequestWithBodyBuilder put(String url) {
        return MockRequestBuilderFactory
                .createMockRequestWithBodyBuilder(createRequest(url, HttpMethod.PUT), getConfiguration(), mockRequest);
    }

    @Override
    public RequestWithBodyBuilder put(URL url) {
        return MockRequestBuilderFactory
                .createMockRequestWithBodyBuilder(createRequest(url.toString(), HttpMethod.PUT), getConfiguration(), mockRequest);

    }

    @Override
    public RequestWithBodyBuilder delete(String url) {
        return MockRequestBuilderFactory
                .createMockRequestWithBodyBuilder(createRequest(url, HttpMethod.DELETE), getConfiguration(), mockRequest);
    }

    @Override
    public RequestWithBodyBuilder delete(URL url) {
        return MockRequestBuilderFactory
                .createMockRequestWithBodyBuilder(createRequest(url.toString(), HttpMethod.DELETE), getConfiguration(), mockRequest);
    }

    @Override
    public RequestWithBodyBuilder patch(String url) {
        return MockRequestBuilderFactory
                .createMockRequestWithBodyBuilder(createRequest(url, HttpMethod.PATCH), getConfiguration(), mockRequest);
    }

    @Override
    public RequestWithBodyBuilder patch(URL url) {
        return MockRequestBuilderFactory
                .createMockRequestWithBodyBuilder(createRequest(url.toString(), HttpMethod.PATCH), getConfiguration(), mockRequest);
    }

    private EasyRequest createRequest(String url, HttpMethod httpMethod) {
        EasyRequest request = new EasyRequest(url, httpMethod, this);
        requests.add(request);
        return request;
    }

    public List<EasyRequest> getRequests() {
        return requests;
    }
}
