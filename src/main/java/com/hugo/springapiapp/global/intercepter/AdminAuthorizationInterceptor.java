package com.hugo.springapiapp.global.intercepter;

import com.hugo.springapiapp.domain.member.constant.Role;
import com.hugo.springapiapp.global.error.ErrorCode;
import com.hugo.springapiapp.global.error.exception.AuthenticationException;
import com.hugo.springapiapp.global.jwt.service.TokenManager;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component // WebConfig에 등록해 주어야 한다.
@RequiredArgsConstructor
public class AdminAuthorizationInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String accessToken = authorizationHeader.split(" ")[1];

        Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
        String role = (String) tokenClaims.get("role");

        if (!Role.ADMIN.equals(Role.valueOf(role))) {
            throw new AuthenticationException(ErrorCode.FORBIDDEN_ADMIN);
        }

        return true;
    }
}
