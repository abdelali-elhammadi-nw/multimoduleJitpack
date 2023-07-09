package com.nimbleways.springboilerplate.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
// Necessary to use @PreAuthorize
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationJwtTokenFilter authenticationJwtTokenFilter;

    public WebSecurityConfig(final UserDetailsServiceImpl userDetailsService,
                             final AuthenticationJwtTokenFilter authenticationJwtTokenFilter) {
        super();
        this.userDetailsService = userDetailsService;
        this.authenticationJwtTokenFilter = authenticationJwtTokenFilter;
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override
    public void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        // TODO: Understand why this line below is mandatory
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    // Expose authentication manager bean
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // Configure the algorithm used to encrypt the password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        // Enable CORS and disables CSRF
        http.cors().and().csrf().disable().exceptionHandling();

        // Handle exceptions
        http.exceptionHandling().authenticationEntryPoint((request, response, ex) -> {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    ex.getMessage());
        });

        // Add JWT Token Filter as an implementation of
        // UsernamePasswordAuthenticationFilter interface
        http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // Do not generate session id
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.logout();
    }
}
