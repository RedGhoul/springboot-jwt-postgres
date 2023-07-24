package com.app.springbootjwtpostgres.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@Configuration // This annotation marks this class as a configuration component that will be managed by Spring.
@EnableWebSecurity // This annotation enables web security for this application.
@EnableGlobalMethodSecurity(prePostEnabled = true) // This annotation enables global security for method-level annotations, such as @PreAuthorize, with pre- and post-invocation checks enabled.
public class WebSecurityConfig { // This class extends WebSecurityConfigurerAdapter class and overrides some methods to customize web security features.

    @Autowired // This annotation allows us to inject the UserDetailsServiceImpl class into this class.
    private UserDetailsServiceImpl userDetailsService;

    @Autowired // This annotation allows us to inject the AuthEntryPointJwt class into this class.
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean // This annotation marks this method as a bean producer that will be managed by Spring.
    public AuthTokenFilter authenticationJwtTokenFilter() { // This method returns an AuthTokenFilter object as a bean.
        return new AuthTokenFilter(); // This line creates a new AuthTokenFilter object and returns it.
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean // This annotation marks this method as a bean producer that will be managed by Spring.
    public PasswordEncoder passwordEncoder() { // This method returns a PasswordEncoder object as a bean.
        return new BCryptPasswordEncoder(); // This line creates a new BCryptPasswordEncoder object and returns it.
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(new AntPathRequestMatcher("/api/auth/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/test/**")).permitAll()
                                .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterAfter(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}