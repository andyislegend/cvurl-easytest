package com.github.corese4rch.cvurl.easytest;

import com.github.corese4rch.cvurl.easytest.domain.MutableRequestConfiguration;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class MutableRequestConfigurationTest {

    @Test
    public void getRequestTimeoutTest() {
        //given
        var configuration = new MutableRequestConfiguration();
        var requestTimeout = Duration.ofSeconds(1);

        //when
        configuration.setRequestTimeout(requestTimeout);

        //then
        assertThat(configuration.getRequestTimeout()).hasValue(requestTimeout);
    }

    @Test
    public void isAcceptCompressedTest() {
        //given
        var configuration = new MutableRequestConfiguration();
        var acceptCompressed = true;

        //when
        configuration.setAcceptCompressed(acceptCompressed);

        //then
        assertThat(configuration.isAcceptCompressed()).isEqualTo(acceptCompressed);
    }

    @Test
    public void isLogEnabledTest() {
        //given
        var configuration = new MutableRequestConfiguration();
        var isLogEnabled = true;

        //when
        configuration.setLogEnabled(isLogEnabled);

        //then
        assertThat(configuration.isLogEnabled()).isEqualTo(isLogEnabled);
    }
}