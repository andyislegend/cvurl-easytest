package com.github.corese4rch.cvurl.easytest.utils;

import static java.lang.String.format;

public class Urls {
    public static final String TEST_URL = "http://test.com";

    private Urls() {
        throw new IllegalStateException(format("Creation of class %s is forbidden", Urls.class));
    }
}
