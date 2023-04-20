package com.usmb.bdgestback.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeHttpRequests()

                // AuthenticationController
                .requestMatchers("/api/auth/**").permitAll()

                // BdController
                .requestMatchers("/api/bd/**").permitAll()

                // SerieController
                .requestMatchers("/api/serie/follow/**").authenticated()
                .requestMatchers("/api/serie/followed/**").authenticated()
                .requestMatchers("/api/serie/**").permitAll()

                // AuthorController
                .requestMatchers("/api/author/follow/**").authenticated()
                .requestMatchers("/api/author/followed/**").authenticated()
                .requestMatchers("/api/author/**").permitAll()

                // CollectionController
                .requestMatchers("/api/collection/**").authenticated()

                // InitDatabaseController
                .requestMatchers("/api/init").authenticated()

                // HomeController
                .requestMatchers("/api/home").permitAll()

                // HomeController
                .requestMatchers("/api/search/**").permitAll()

                // SharedBdController
                .requestMatchers("/api/sharedbd").authenticated()

                // Other
                .anyRequest().authenticated()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
