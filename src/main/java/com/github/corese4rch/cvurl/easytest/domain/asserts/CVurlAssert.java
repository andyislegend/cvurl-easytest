package com.github.corese4rch.cvurl.easytest.domain.asserts;

import com.github.corese4rch.cvurl.easytest.domain.EasyCVurl;
import com.github.corese4rch.cvurl.easytest.domain.EasyRequest;
import com.github.corese4rch.cvurl.easytest.domain.asserts.rule.Rule;
import com.github.corese4rch.cvurl.easytest.domain.asserts.rule.Rules;
import org.assertj.core.api.AbstractAssert;

import java.util.stream.Stream;

public class CVurlAssert extends AbstractAssert<CVurlAssert, EasyCVurl> {

    private static final String REQUEST_NUMBER_ERROR =
            "Expected number of requests that passes rules <%s> to be <%s>, actual <%s>";

    private static final Rule<EasyRequest> IS_EXECUTED_PREDICATE =
            Rules.of(r -> r.getTimesExecuted() > 0, "executed");

    private final int actualNumOfRequests;

    private CVurlAssert(EasyCVurl easyCVurl, Class<?> selfType) {
        super(easyCVurl, selfType);
        this.actualNumOfRequests = easyCVurl.getRequests().size();
    }

    public static CVurlAssert assertThat(EasyCVurl cvurl) {
        return new CVurlAssert(cvurl, CVurlAssert.class);
    }

    public CVurlAssert hasRequests(Rule<Integer> requestsNumRule) {
        if (requestsNumRule.isNotValid(actualNumOfRequests)) {
            failWithMessage(REQUEST_NUMBER_ERROR, "created", requestsNumRule.getDescription(), actualNumOfRequests);
        }
        return this;
    }

    public CVurlAssert hasRequests(Rule<Integer> requestsNumRule, Rule<EasyRequest> predicate) {
        return failIfNotValid(requestsNumRule, predicate);
    }

    public CVurlAssert hasExecutedRequests(Rule<Integer> requestsNumRule) {
        return failIfNotValid(requestsNumRule, IS_EXECUTED_PREDICATE);
    }

    public CVurlAssert hasExecutedRequests(Rule<Integer> requestsNumRule, Rule<EasyRequest> predicate) {
        return failIfNotValid(requestsNumRule, Rules.and(IS_EXECUTED_PREDICATE, predicate));
    }

    private CVurlAssert failIfNotValid(Rule<Integer> requestsNumRule, Rule<EasyRequest> rule) {
        int actualNum = (int) getRequestsThat(rule).count();
        if (requestsNumRule.isNotValid(actualNum)) {
            failWithMessage(REQUEST_NUMBER_ERROR, rule.getDescription(), requestsNumRule.getDescription(), actualNum);
        }

        return this;
    }

    private Stream<EasyRequest> getRequestsThat(Rule<EasyRequest> rule) {
        return this.actual.getRequests().stream().filter(rule::isValid);
    }
}
