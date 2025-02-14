package org.project.spring_security_client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashSet;
import java.util.Set;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    private static final String[] WHITE_LIST_URLS = {
            "/api/hello",
            "/register",
            "/verifyRegistration*",
            "/resendVerifyToken*",
            "/changePassword*",
            "/resetPassword",
            "/savePassword",
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(WHITE_LIST_URLS).permitAll()
                        .requestMatchers("/api/**").authenticated())
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/oauth2/authorization/api-client-oidc"));

        return httpSecurity.build();
    }

    @Bean
    public GrantedAuthoritiesMapper grantedAuthoritiesMapper() {
        return authorities -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            authorities.forEach(authority -> {
                GrantedAuthority mappedAuthority;

                if (authority instanceof OidcUserAuthority) {
                    OidcUserAuthority userAuthority = (OidcUserAuthority) authority;
                    mappedAuthority = new OidcUserAuthority(
                            "OIDC_USER", userAuthority.getIdToken(), userAuthority.getUserInfo());
                } else if (authority instanceof OAuth2UserAuthority) {
                    OAuth2UserAuthority userAuthority = (OAuth2UserAuthority) authority;
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
