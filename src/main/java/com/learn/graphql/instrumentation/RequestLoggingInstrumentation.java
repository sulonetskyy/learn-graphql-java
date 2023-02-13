package com.learn.graphql.instrumentation;

import graphql.ExecutionResult;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.SimpleInstrumentation;
import graphql.execution.instrumentation.SimpleInstrumentationContext;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestLoggingInstrumentation extends SimpleInstrumentation {

    private final Clock clock;

    @Override
    public InstrumentationContext<ExecutionResult> beginExecution(InstrumentationExecutionParameters parameters) {
        var start = Instant.now(clock);

        // Here is the correlation_id per graphQl request.
        // See {@code LoggingListener} to find correlation_id per service request
        //MDC.put(CORRELATION_ID, parameters.getExecutionInput().getExecutionId().toString());

        log.info("Query [{}] with vars {}.", parameters.getQuery(), parameters.getVariables());

        return SimpleInstrumentationContext.whenCompleted((executionResult, throwable) -> {
            var duration = Duration.between(start, Instant.now(clock));
            if (throwable == null) {
                log.info("Completed successfully in {}", duration);
            } else {
                log.warn("Failed in {}", duration, throwable);
            }
            //MDC.clear();
        });
    }

}
