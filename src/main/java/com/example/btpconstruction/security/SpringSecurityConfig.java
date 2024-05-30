package com.example.testsessionandspringsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;



    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/error403") // Redirection vers la page d'erreur 403
                )
                .authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers("/build/**", "/dist/**", "/plugins/**",  "/login","/register","/favicon.ico", "/error403","/cssPdf/**").permitAll();
                            auth.requestMatchers("/","/add-to-cart","remove-to-cart").authenticated();
                            auth.requestMatchers("/testAdmin").hasRole("ADMIN");
                            auth.requestMatchers("/testUser","/pdf/**").hasRole("USER");
//                            auth.requestMatchers("/haha").hasRole("ADMIN");

                            /*auth.requestMatchers("/rh").hasRole("RH");
                            auth.requestMatchers("/service").hasRole("SERVICE");
                            auth.requestMatchers("/user").hasRole("USER");*/

//                            auth.anyRequest().authenticated();
                            auth.anyRequest().permitAll();
                        }
                ).formLogin(
                        form -> {
                            form.loginPage("/login");
                            form.defaultSuccessUrl("/", true);
                            form.failureUrl("/login?error");
                            form.usernameParameter("email");
                            form.passwordParameter("password");
                        }
                )

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(-1)
                        .maxSessionsPreventsLogin(false)
                )
                .logout(
                        logout -> {
                            logout.logoutUrl("/logout"); // URL de déconnexion
                            logout.logoutSuccessUrl("/login?logout"
                            ); // URL de redirection après la déconnexion
                            logout.invalidateHttpSession(true); // Invalider la session HTTP
                            logout.deleteCookies("JSESSIONID");
                            logout.permitAll();// Supprimer le cookie de session
                        }
                )
                .build();
    }




    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }





}

