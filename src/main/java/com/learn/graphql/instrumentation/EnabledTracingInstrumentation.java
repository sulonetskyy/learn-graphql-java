package com.learn.graphql.instrumentation;

import graphql.execution.instrumentation.tracing.TracingInstrumentation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "graphql.servlet.tracing-enabled", havingValue = "true")
public class EnabledTracingInstrumentation extends TracingInstrumentation {
}
