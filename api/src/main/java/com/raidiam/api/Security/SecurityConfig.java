package com.raidiam.api.Security;

// By Otavio


import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    // Necessarie to  processes any Http requests
    public SecurityFilterChain securityFilterChain (HttpSecurity http ) throws Exception  {

        // Whenever any Http request comes, authorize only if client authenticated
        http.authorizeHttpRequests(authorize -> authorize.
                anyRequest().authenticated())

                // Config to make Spring deal with opaque tokens
                .oauth2ResourceServer(oauth2 -> oauth2.opaqueToken(token -> {}));

        return http.build();
    }


}
