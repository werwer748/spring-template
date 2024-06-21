package com.hugo.springapiapp.global.config.jpa;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing // jpa auditing 활성화
public class JpaConfig {

    private final HttpServletRequest httpServletRequest;

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl(httpServletRequest);
    }
}
