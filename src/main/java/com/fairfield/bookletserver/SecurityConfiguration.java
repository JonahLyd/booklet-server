package com.fairfield.bookletserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.ForwardLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration  {

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web
        .ignoring()
        .requestMatchers(
            "/css/**",
            "/javascript/**",
            "/files/level1/**",
            "/files/level2/**",
            "/files/**",
            "/images/**"
        );
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
        authorizationManagerRequestMatcherRegistry
            .requestMatchers("/admin/**")
            .hasRole("ADMIN")
            .requestMatchers("/login*")
            .permitAll()
            .anyRequest()
            .authenticated()
        )
        .userDetailsService(userDetailsService())
        .formLogin(httpSecurityFormLoginConfigurer ->
            httpSecurityFormLoginConfigurer
                .loginPage("/login")
                .loginProcessingUrl("/authenticate")
                .defaultSuccessUrl("/index", true)
                .failureUrl("/login?error=true")
                .failureHandler(authenticationFailureHandler())
        )
        .logout(httpSecurityLogoutConfigurer ->
            httpSecurityLogoutConfigurer.logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(logoutSuccessHandler()));
    return http.build();
  }

  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    UserDetails admin1 = User
        .withUsername("jonah.lydon@student.fairfield.edu")
        .password(passwordEncoder().encode("pwd"))
        .roles("ADMIN")
        .build();
    UserDetails user1 = User
        .withUsername("mateo.davalos@student.fairfield.edu")
        .password(passwordEncoder().encode("pwd"))
        .roles("USER")
        .build();
    UserDetails user2 = User
        .withUsername("habibul.huq@student.fairfield.edu")
        .password(passwordEncoder().encode("pwd"))
        .roles("USER")
        .build();
    return new InMemoryUserDetailsManager(user1, user2, admin1);
  }

  public InMemoryUserDetailsManager updateUserService(com.fairfield.bookletserver.entity.User user) {
    var role = user.getLevelId() == 2L ? "ADMIN" : "USER";
    UserDetails newUser = User
        .withUsername(user.getUsername())
        .password(passwordEncoder().encode(user.getPassword()))
        .roles(role)
        .build();
    var service = userDetailsService();
    service.createUser(newUser);

    return service;
  }

  public InMemoryUserDetailsManager updateUserPassword(com.fairfield.bookletserver.entity.User user) {
    var service = userDetailsService();
    service.updatePassword(service.loadUserByUsername(user.getUsername()), passwordEncoder().encode(user.getPassword()));

    return service;
  }

  @Bean
  public LogoutSuccessHandler logoutSuccessHandler() {
    return new ForwardLogoutSuccessHandler("/login");
  }

  @Bean
  public AuthenticationFailureHandler authenticationFailureHandler() {
    return new AuthenticationEntryPointFailureHandler(new BasicAuthenticationEntryPoint());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
