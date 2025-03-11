package com.egg.biblioteca;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SeguridadWeb {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/admin/**").hasRole("ADMIN") // Todo lo que puede acceder el Admin
                .requestMatchers("/css/**", "/js/**", "/img/**").permitAll() // Recursos
                .requestMatchers("/", "/login", "/registrar", "/registro").permitAll() // Paginas/Endpoints disponibles para todo el publico
                .anyRequest().authenticated() // El resto requiere que estes autenticado
            )
            .csrf((csrf) -> csrf.disable())
            .formLogin((form) -> form
                .loginPage("/login")
                .loginProcessingUrl("/logincheck")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/inicio", true)
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );
            
        return http.build();
    }
}
