package com.hugo.springapiapp.global.config;

import com.hugo.springapiapp.global.jwt.service.TokenManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // TokenManager를 등록하기위해서...
public class SecurityConfig {

    @Value("${token.access-token-expiration-time}")
    private String accessTokenExpirationTime;

    @Value("${token.refresh-token-expiration-time}")
    private String refreshTokenExpirationTime;

    @Value("${token.secret}")
    private String tokenSecret;

    @Bean
    public TokenManager tokenManager() {
        return new TokenManager(accessTokenExpirationTime, refreshTokenExpirationTime, tokenSecret); // 생성자 주입
    }
}
