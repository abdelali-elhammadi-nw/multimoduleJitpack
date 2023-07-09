package com.nimbleways.springboilerplate.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.List.of;
import static java.util.Optional.ofNullable;

@Component
public class AuthenticationJwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    private final UserDetailsServiceImpl userDetailsService;

    
    // TODO : move to application.yml
    private static final String JWT_PREFIX = "Bearer ";

    @Autowired
    public AuthenticationJwtTokenFilter(final JwtTokenUtil jwtTokenUtil, final UserDetailsServiceImpl userDetailsService) {
        super();
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain chain) throws ServletException, IOException {

        // Get Authorization header
        Cookie[] cookies = request.getCookies();
        List<Cookie> header = new ArrayList<>();
        if(cookies != null){
            header =Arrays.stream(cookies)
                    .filter(c -> c.getName().equals("token")).collect(Collectors.toList());
        }

        final boolean tokenExists = header.size() == 1 && header.get(0) != null;

        if (tokenExists == false) {
            chain.doFilter(request, response);
            return;
        }

        // If token is invalid, pass request to next filter
        final String token = header.get(0).getValue();

        if (!jwtTokenUtil.validate(token)) {
            chain.doFilter(request, response);
            return;
        }

        // If token is valid, get user identity and set it on the spring security context
        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtTokenUtil.getUsername(token));
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                ofNullable(userDetails).map(UserDetails::getAuthorities).orElse(of()));

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }
}