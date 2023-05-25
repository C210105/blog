package net.shop2k.blog.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(authz -> authz
            .requestMatchers(org.springframework.boot.autoconfigure.security.servlet.PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .requestMatchers("/blog").hasAnyRole("USER", "ADMIN")
            .requestMatchers("/admin/blog/").hasRole("ADMIN")
            .anyRequest().permitAll() 
        )
        .formLogin(login -> login
            .loginProcessingUrl("/admin/blog/login")
            .loginPage("/admin/blog/login")
            .defaultSuccessUrl("/admin/blog/index")
            .failureUrl("/admin/blog/login?error=true")
            .permitAll()
        );
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

