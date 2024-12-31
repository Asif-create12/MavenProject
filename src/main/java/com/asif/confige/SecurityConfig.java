package com.asif.confige;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {
    private JWTFilter jwtFilter;

    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception {
        //h(cd)2
        //this is for third party app
        http.csrf().disable().cors().disable();
        //haap
        //this is say open all the url
       // http.authorizeHttpRequests().anyRequest().permitAll();
        http.addFilterBefore(jwtFilter, AuthorizationFilter.class);
        http.authorizeHttpRequests().requestMatchers("/api/v1/auth/**")
                //this line means permit all url
                .permitAll().requestMatchers("/api/v1/dummy/getMessage")
                .hasAnyRole("ADMIN","USER").anyRequest().authenticated();
            // this line means just admin and user can access the url
        //has role means only one user can access but you right hasAnyRole multiple user can access
                                //** meaning after that url all open

        return http.build();


    }

}
