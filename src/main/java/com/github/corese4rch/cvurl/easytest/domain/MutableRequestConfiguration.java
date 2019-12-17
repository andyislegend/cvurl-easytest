package com.github.corese4rch.cvurl.easytest.domain;

import coresearch.cvurl.io.internal.configuration.RequestConfiguration;
import lombok.Setter;

import java.time.Duration;
import java.util.Optional;

@Setter
public class MutableRequestConfiguration extends RequestConfiguration {

    private Duration requestTimeout;
    private boolean acceptCompressed;
    private boolean logEnabled;

    @Override
    public Optional<Duration> getRequestTimeout() {
        return Optional.of(this.requestTimeout);
    }

    @Override
    public boolean isAcceptCompressed() {
        return acceptCompressed;
    }

    @Override
    public boolean isLogEnabled() {
        return logEnabled;
    }
}
