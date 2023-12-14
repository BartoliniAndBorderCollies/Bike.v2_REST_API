package com.klodnicki.Bike.v2.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 * This class is responsible for the security configuration of the application.
 * @EnableWebSecurity: Enables Spring Security's web security support.
 * @Configuration: Indicates that this class declares one or more @Bean methods.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    /**
     * This method configures the security filter chain.
     * It disables CSRF, which means that now the application is not protected against Cross-Site Request Forgery attacks.
     * It authorizes all requests sent to /api/admin/**, which means that before a response is sent, the client must go through the authorization process.
     * 'Has role' means that besides authorization, this client must also have a specific role of ADMIN.
     * Any other request sent to other paths doesn't need to be authorized.
     * Disabling FrameOptionsConfig enables access to the H2 console.
     * Form login and logout means there is a basic login panel and checkout procedure.
     * HTTP Basic enables the use of BasicAuth in client applications like Postman to be able to send requests.
     * @param http HttpSecurity object to configure the security settings.
     * @param mvc MvcRequestMatcher.Builder object to match the request pattern.
     * @return SecurityFilterChain object after applying all the configurations.
     * @throws Exception if any exception occurs during the configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(mvc.pattern("/api/admin/**"))
                        .hasRole("ADMIN")
//                        .authenticated()// teraz jest tylko logowanie
                        .anyRequest()
                        .permitAll())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) //ta linia umozliwia dostep do bazy danych h2 console
                .formLogin(Customizer.withDefaults())
//                .formLogin(formLogin -> formLogin.loginPage("/login")
//                        .failureForwardUrl("/login?error")
//                        .successForwardUrl("/home?loginSuccess"))
                .logout(Customizer.withDefaults())
//                .logout(formLogout -> formLogout
//                        .logoutUrl("/custom-logout")
//                        .logoutRequestMatcher(new AntPathRequestMatcher("/custom-logout", "GET"))
//                        .logoutSuccessUrl("/login?logout=true")
//                        .invalidateHttpSession(true)
//                        .clearAuthentication(true)
//                        .deleteCookies("JSESSIONID"))
                .httpBasic(Customizer.withDefaults())//ta linia umożliwa BasicAuth w postmanie
                .build();
    }

    /**
     * This method provides a PasswordEncoder bean.
     * BCryptPasswordEncoder is a password encoder that uses BCrypt hashing algorithm.
     * @return PasswordEncoder object that uses BCrypt hashing algorithm.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
