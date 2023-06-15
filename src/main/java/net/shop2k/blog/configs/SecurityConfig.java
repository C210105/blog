package net.shop2k.blog.configs;

import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(authz -> authz
            .requestMatchers(org.springframework.boot.autoconfigure.security.servlet.PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .requestMatchers("/blog").hasAnyRole("USER", "ADMIN", "MANAGER")
            .requestMatchers("/admin/blog/").hasRole("ADMIN")
            .requestMatchers("/manager/blog/").hasRole("MANAGER")
            .anyRequest().permitAll() 
        )
        .formLogin(login -> login
            //デフォルトのログインフォーム
            .loginProcessingUrl("/blog/login")
            .loginPage("/blog/login")
            .successHandler((request, response, authentication) -> {
                // ROLEの情報を受け取り
                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                for (GrantedAuthority authority : authorities) {
                    // ADMIN -> 
                    if (authority.getAuthority().equals("ROLE_ADMIN")) {
                        response.sendRedirect("/admin/blog/index");
                        log.info("ADMINとしてログイン出来た");
                        return;
                    //MANAGER ->    
                    }if(authority.getAuthority().equals("ROLE_MANAGER")){
                        log.info("MANAGERとしてログイン出来た");
                        response.sendRedirect("/manager/blog/index");
                        return;
                    }
                }
                // 以外 ->
                log.info("エラー");
                response.sendRedirect("/admin/blog/error");
            })
            .failureUrl("/blog/login?error=true")
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/blog/logout") //logout　ページ
            .logoutSuccessUrl("/blog/login") //logout。後
            .invalidateHttpSession(true) //sessionを削除する
            .deleteCookies("JSESSIONID")); //cookiesを削除する
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

