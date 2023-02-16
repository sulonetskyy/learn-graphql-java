package com.learn.graphql;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.LocalDate;

import static java.time.ZoneOffset.UTC;

@Configuration
@Import(Application.class)
public class TestApplication {

    @Bean
    @Primary
    public Clock testClock() {
        // set static dates for tests responses
        return Clock.fixed(LocalDate.of(2020, 10, 1).atStartOfDay(UTC).toInstant(), UTC);
    }
}
