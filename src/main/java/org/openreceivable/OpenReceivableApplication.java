package org.openreceivable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Application for Open Receivable System
 * Reactive GraphQL API with Federation v2.9 support
 */
@SpringBootApplication
public class OpenReceivableApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenReceivableApplication.class, args);
    }
}
