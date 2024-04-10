package com.quocanhit.taskuserservice.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Configuration
public class ApplicationConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.sessionManagement(
                        management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).authorizeHttpRequests(
                        authorize -> authorize.requestMatchers("/api/auth/**").authenticated().anyRequest().permitAll()
                ).addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                //.cors(cors -> cors.configurationSource(configurationSource()))
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

    private CorsConfigurationSource configurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Collections.singletonList("*"));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setExposedHeaders(Arrays.asList("Authorization"));
                config.setMaxAge(3600L);

                return config;
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //bang
//    private JwtTokenValidator jwtTokenValidator;
//    private  JwtProvider jwtProvider;
//
//    private AuthenticationProvider authenticationProvider;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        RequestMatcher publicUrls = new OrRequestMatcher(
//                new AntPathRequestMatcher("/api/v1/auth/**"),
//                new AntPathRequestMatcher("/v2/api-docs"),
//                new AntPathRequestMatcher("/v3/api-docs"),
//                new AntPathRequestMatcher("/v3/api-docs/**"),
//                new AntPathRequestMatcher("/swagger-resources"),
//                new AntPathRequestMatcher("/swagger-resources/**"),
//                new AntPathRequestMatcher("/configuration/ui"),
//                new AntPathRequestMatcher("/configuration/security"),
//                new AntPathRequestMatcher("/swagger-ui/**"),
//                new AntPathRequestMatcher("/webjars/**"),
//                new AntPathRequestMatcher("/swagger-ui.html"),
//                new AntPathRequestMatcher("/bot")
//        );
//
//        http
//                .csrf()
//                .disable()
//                .authorizeRequests()
//                .requestMatchers(publicUrls)
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(new JwtTokenValidator(), UsernamePasswordAuthenticationFilter.class)
//        ;
//
//        return http.build();
//    }

}
