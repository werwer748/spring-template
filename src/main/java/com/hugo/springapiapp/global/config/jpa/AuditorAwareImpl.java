package com.hugo.springapiapp.global.config.jpa;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.util.StringUtils;

import java.util.Optional;

/*
* Audit 사전적 정의 : 감시하다.
* 엔티티가 저장 또는 수정될 때 생성일자, 수정일자, 생성자, 수정자 변경 시 자동으로 값을 넣어 주도록 설정
* @EnableJpaAuditing : JPA Auditing 기능을 사용하기 위한 어노테이션
*/
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {

    private final HttpServletRequest httpServletRequest;

    @Override
    public Optional<String> getCurrentAuditor() {
        String modifiedBy = httpServletRequest.getRequestURI();
        if (!StringUtils.hasText(modifiedBy)) {
            modifiedBy = "unknown";
        }
        return Optional.of(modifiedBy);
    }
}
