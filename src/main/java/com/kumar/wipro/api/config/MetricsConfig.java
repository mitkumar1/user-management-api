package com.kumar.wipro.api.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Custom metrics configuration for monitoring
 */
@Configuration
@Profile("monitoring")
public class MetricsConfig {

    @Bean
    public Counter userRegistrationCounter(MeterRegistry meterRegistry) {
        return Counter.builder("user.registration.total")
                .description("Total number of user registrations")
                .tag("status", "success")
                .register(meterRegistry);
    }

    @Bean
    public Counter userLoginCounter(MeterRegistry meterRegistry) {
        return Counter.builder("user.login.total")
                .description("Total number of user logins")
                .register(meterRegistry);
    }

    @Bean
    public Timer userLoginTimer(MeterRegistry meterRegistry) {
        return Timer.builder("user.login.duration")
                .description("User login duration")
                .register(meterRegistry);
    }

    @Bean
    public Counter authenticationFailureCounter(MeterRegistry meterRegistry) {
        return Counter.builder("authentication.failures.total")
                .description("Total number of authentication failures")
                .register(meterRegistry);
    }

    @Bean
    public Counter apiRequestCounter(MeterRegistry meterRegistry) {
        return Counter.builder("api.requests.total")
                .description("Total number of API requests")
                .register(meterRegistry);
    }
}
