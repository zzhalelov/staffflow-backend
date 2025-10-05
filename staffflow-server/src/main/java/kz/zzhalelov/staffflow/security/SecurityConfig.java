//package kz.zzhalelov.staffflow.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    // Пользователь "admin" с паролем "secret123"
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.withUsername("admin")
//                .password("{noop}secret123") // {noop} значит "без шифрования"
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
//
//    // Настройка HTTP безопасности
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable() // для Postman и API можно отключить CSRF
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/**").authenticated() // защищаем только API
//                        .anyRequest().permitAll()
//                )
//                .httpBasic(); // Basic Auth
//        return http.build();
//    }
//}