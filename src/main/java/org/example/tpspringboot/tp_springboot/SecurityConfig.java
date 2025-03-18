package org.example.tpspringboot.tp_springboot;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}admin")  // No password encoding
                .roles("ADMIN")
                .and()
                .withUser("user")
                .password("{noop}user")
                .roles("USER");
    }


    public void checkRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Current roles: " + authentication.getAuthorities());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()   // pour activer le cors
                .csrf().disable() // Disable CSRF if needed
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/**").permitAll()  // Allow POST for all
                .antMatchers(HttpMethod.PUT, "/**").permitAll()   // Allow PUT for all
                .antMatchers(HttpMethod.GET, "/**").permitAll()   // Allow GET for all
                .antMatchers(HttpMethod.DELETE, "/**").permitAll()   // Only allow DELETE for ADMIN defined on service
                .anyRequest().authenticated()  // Secure other requests
                .and()
                .httpBasic();  // Enable basic authentication or choose another authentication method
    }
}
