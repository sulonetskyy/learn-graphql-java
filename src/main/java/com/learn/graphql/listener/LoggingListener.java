package com.learn.graphql.listener;

import graphql.kickstart.servlet.core.GraphQLServletListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingListener implements GraphQLServletListener {

    public static String CORRELATION_ID = "correlation_id";

    private final Clock clock;

    @Override
    public RequestCallback onRequest(HttpServletRequest request, HttpServletResponse response) {

        var startTime = Instant.now(clock);
        log.info("Received graphql request.");

        return new RequestCallback() {
            {
                MDC.put(CORRELATION_ID, UUID.randomUUID().toString());
            }

            @Override
            public void onParseError(HttpServletRequest request, HttpServletResponse response, Throwable throwable) {
                RequestCallback.super.onParseError(request, response, throwable);
                log.info("onParseError");
            }

            @Override
            public void beforeFlush(HttpServletRequest request, HttpServletResponse response) {
                RequestCallback.super.beforeFlush(request, response);
                log.info("beforeFlush");
            }

            @Override
            public void onSuccess(HttpServletRequest request, HttpServletResponse response) {
                RequestCallback.super.onSuccess(request, response);
                log.info("onSuccess");
            }

            @Override
            public void onError(HttpServletRequest request, HttpServletResponse response, Throwable throwable) {
                RequestCallback.super.onError(request, response, throwable);
                log.info("onError");
            }

            @Override
            public void onFinally(HttpServletRequest request, HttpServletResponse response) {
                RequestCallback.super.onFinally(request, response);
                log.info("RequestCallback: Completing request. Time taken: {}",
                        Duration.between(startTime, Instant.now(clock)));
                MDC.remove(CORRELATION_ID);
            }
        };
    }

}
