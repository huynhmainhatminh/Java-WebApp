package com.ev.batteryswap.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // trang công khai
                        .requestMatchers(
                                "/", "/login", "/register", "/packages",
                                "/admin/login", "/staff/login", // Cho phép 2 trang login
                                "/api/**", // Cho phép mọi API
                                "/css/**", "/js/**", "/img/**" // Cho phép file tĩnh
                        ).permitAll()

                        //trang cho driver
                        .requestMatchers("/my", "/naptien", "/contact", "/dashboard").hasAnyAuthority("DRIVER")

                        //trang cho staff
                        .requestMatchers("/staff/**").hasAnyAuthority("STAFF")

                        //trang cho admin
                        .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}