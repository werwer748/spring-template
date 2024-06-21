package com.hugo.springapiapp.global.intercepter;

import com.hugo.springapiapp.global.error.ErrorCode;
import com.hugo.springapiapp.global.error.exception.AuthenticationException;
import com.hugo.springapiapp.global.jwt.cosntant.TokenType;
import com.hugo.springapiapp.global.jwt.service.TokenManager;
import com.hugo.springapiapp.global.util.AuthorizationHeaderUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component // WebConfig에 등록해 주어야 한다.
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;

    @Override //? preHandle: 컨트롤러 로직을 실행하기 전에 먼저 수행되는 부분
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. Authorization Header 검증
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);

        // 2. 토큰 검증
        String token = authorizationHeader.split(" ")[1];
        tokenManager.validateToken(token);

        // 3. 토큰 타입
        Claims tokenClaims = tokenManager.getTokenClaims(token);
        String tokenType = tokenClaims.getSubject();
        if (!TokenType.isAccessToken(tokenType)) {
            throw new AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);
        }
        return true; // 검증이 정상적으로 이뤄지는 경우 true 반호나 => 컨트롤러 정상 수행
    }
}
