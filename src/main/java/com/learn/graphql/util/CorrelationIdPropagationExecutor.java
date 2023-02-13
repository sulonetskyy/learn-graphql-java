package com.learn.graphql.util;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;

import java.util.concurrent.Executor;

import static com.learn.graphql.listener.LoggingListener.CORRELATION_ID;


/**
 * Thread executor wrapper to put the correlation id into the thread.
 */
@RequiredArgsConstructor
public class CorrelationIdPropagationExecutor implements Executor {

    private final Executor delegate;

    public static Executor wrap(Executor executor) {
        return new CorrelationIdPropagationExecutor(executor);
    }

    @Override
    public void execute(@NotNull Runnable runnable) {
        var correlationId = MDC.get(CORRELATION_ID);
        delegate.execute(() -> {
            try {
                MDC.put(CORRELATION_ID, correlationId);
                runnable.run();
            } finally {
                MDC.remove(CORRELATION_ID);
            }
        });
    }
}
