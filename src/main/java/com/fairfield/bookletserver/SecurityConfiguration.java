package com.fairfield.bookletserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class SecurityConfiguration  {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(a -> a
            .requestMatchers("/login", "/error", "/webjars/**").permitAll()
            .requestMatchers("/index", "/").authenticated()
            .anyRequest().authenticated()
        )
        .exceptionHandling(e -> e
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        )
        .formLogin(login -> login
            .loginPage("/login")
            .permitAll()
            .successForwardUrl("/")
        )
        .oauth2Login((oauth2Login) -> oauth2Login
            .userInfoEndpoint((userInfo) -> userInfo
                .userAuthoritiesMapper(grantedAuthoritiesMapper())
            )
        )
        .logout(logout -> logout
            .logoutSuccessUrl("/login")
            .permitAll()
        )
        .csrf(csrf -> csrf
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        );
    return http.build();
  }

  private GrantedAuthoritiesMapper grantedAuthoritiesMapper() {
    return (authorities) -> {
      Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

      authorities.forEach((authority) -> {
        GrantedAuthority mappedAuthority;

        if (authority instanceof OidcUserAuthority userAuthority) {
          mappedAuthority = new OidcUserAuthority(
              "OIDC_USER", userAuthority.getIdToken(), userAuthority.getUserInfo());
        } else if (authority instanceof OAuth2UserAuthority userAuthority) {
          mappedAuthority = new OAuth2UserAuthority(
              "OAUTH2_USER", userAuthority.getAttributes());
        } else {
          mappedAuthority = authority;
        }

        mappedAuthorities.add(mappedAuthority);
      });

      return mappedAuthorities;
    };
  }
}
