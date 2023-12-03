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

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
