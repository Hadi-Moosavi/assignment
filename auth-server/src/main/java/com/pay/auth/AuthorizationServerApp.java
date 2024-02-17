package com.pay.auth;

import com.pay.auth.config.KeycloakServerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = LiquibaseAutoConfiguration.class)
@EnableConfigurationProperties(KeycloakServerProperties.class)
@Slf4j
public class AuthorizationServerApp {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApp.class, args);
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> onApplicationReadyEventListener(
            ServerProperties serverProperties, KeycloakServerProperties keycloakServerProperties) {
        return evt -> {
            Integer port = serverProperties.getPort();
            String keycloakContextPath = keycloakServerProperties.getContextPath();
            log.info("Embedded Keycloak started: http://localhost:{}{} to use keycloak",
                    port, keycloakContextPath);
        };
    }
}
