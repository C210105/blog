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
            // .defaultSuccessUrl("/admin/blog/index")
            .successHandler((request, response, authentication) -> {
                // Lấy thông tin vai trò của người dùng đã đăng nhập
                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                for (GrantedAuthority authority : authorities) {
                    if (authority.getAuthority().equals("ROLE_ADMIN")) {
                        // Nếu là admin, chuyển hướng đến trang admin
                        response.sendRedirect("/admin/blog/index");
                        return;
                    }
                }
                // Mặc định, chuyển hướng đến trang user
                response.sendRedirect("/admin/blog/error");
            })
            .failureUrl("/admin/blog/login?error=true")
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/admin/blog/logout") //logout　ページ
            .logoutSuccessUrl("/admin/blog/login") //logout。後
            .invalidateHttpSession(true) //sessionを削除する
            .deleteCookies("JSESSIONID")); //cookiesを削除する
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

