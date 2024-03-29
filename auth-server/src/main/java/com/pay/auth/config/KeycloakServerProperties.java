package com.pay.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak.server")
@Getter
@Setter
public class KeycloakServerProperties {
    String contextPath = "/auth";
    String realmImportFile = "assignment-realm.json";
    AdminUser adminUser = new AdminUser();
    @Getter
    @Setter
    public static class AdminUser {
        String username = "admin";
        String password = "admin";
    }
}
