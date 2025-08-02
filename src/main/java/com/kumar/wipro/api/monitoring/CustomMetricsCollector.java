package com.kumar.wipro.api.monitoring;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Custom metrics collector for business events
 */
@Component
public class CustomMetricsCollector {

    private static final Logger logger = LoggerFactory.getLogger(CustomMetricsCollector.class);

    private final MeterRegistry meterRegistry;
    private final Counter userRegistrationCounter;
    private final Counter userLoginCounter;
    private final Counter authFailureCounter;
    private final Timer loginTimer;

    @Autowired
    public CustomMetricsCollector(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.userRegistrationCounter = Counter.builder("user_registration_total")
                .description("Total user registrations")
                .register(meterRegistry);
        
        this.userLoginCounter = Counter.builder("user_login_total")
                .description("Total user logins")
                .register(meterRegistry);
        
        this.authFailureCounter = Counter.builder("authentication_failure_total")
                .description("Total authentication failures")
                .register(meterRegistry);
        
        this.loginTimer = Timer.builder("user_login_duration_seconds")
                .description("User login duration")
                .register(meterRegistry);
    }

    public void recordUserRegistration() {
        userRegistrationCounter.increment();
        logger.info("User registration metric recorded");
    }

    public void recordUserLogin(String username) {
        userLoginCounter.increment();
        logger.info("User login metric recorded for user: {}", username);
    }

    public void recordAuthenticationFailure(String reason) {
        authFailureCounter.increment();
        logger.warn("Authentication failure recorded: {}", reason);
    }

    public Timer.Sample startLoginTimer() {
        return Timer.start(meterRegistry);
    }

    public void recordLoginDuration(Timer.Sample sample) {
        sample.stop(loginTimer);
    }

    public void recordCustomMetric(String name, double value) {
        meterRegistry.gauge(name, value);
        logger.debug("Custom metric recorded: {} = {}", name, value);
    }
}
