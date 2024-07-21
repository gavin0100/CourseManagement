package com.example.back_end_fams.config;

import com.example.back_end_fams.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@Component
@RequiredArgsConstructor
@Configuration
public class ApplicationConfig {
    private final UserService userService;

    public UserDetailsService userDetailsService(){
        return userService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        System.out.println("truy cap authenticationManager trong applicationConfig");
        return configuration.getAuthenticationManager();
    }
    @Bean
    public StandardServletMultipartResolver multipartResolver() {

        System.out.println("truy cap multipartResolver trong appliactionConfig" );
        return new StandardServletMultipartResolver();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        System.out.println("truy cap authenticationProvider trong applicationConfig");
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(userDetailsService());
        dao.setPasswordEncoder(passwordEncoder());
        return dao;
    }

    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

}
