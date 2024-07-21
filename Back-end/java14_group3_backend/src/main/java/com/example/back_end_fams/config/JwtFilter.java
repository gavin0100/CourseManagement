package com.example.back_end_fams.config;

import com.example.back_end_fams.authentication.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.HOST;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    private final UserDetailsService userDetailsService;
    private  final JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        System.out.println(request.getHeader("Host"));
        System.out.println("truy cap doFilterinteral trong jwtFilter");
        final String authHeader = request.getHeader(AUTHORIZATION);
        final String jwt;
        final String email;
//        System.out.println(authHeader);
        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            jwt = authHeader.substring(7);
            email = jwtService.extractEmail(jwt);
        }else{
            if (authHeader != null){
                logger.error("JWT Token does not begin with Bearer String");
            }
            else
                filterChain.doFilter(request, response);
            return;
        }

        if(email!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            if(jwtService.isValidToken(jwt, userDetails)){
                System.out.println(userDetails.getUsername());
                System.out.println(userDetails.getPassword());
                System.out.println(userDetails.getAuthorities().toString());
                System.out.println("===");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String username = auth.getName(); // get username
                Object credentials = auth.getCredentials();
                System.out.println(username +  " " + credentials);
                System.out.println(auth.getDetails().toString());
//                System.out.println("SecurityContextHolder.getContext: " + SecurityContextHolder.getContext().toString());
            }
        }
        filterChain.doFilter(request, response);
    }
}
