package com.hugo.springapiapp.api.logout.service;

import com.hugo.springapiapp.domain.member.entity.Member;
import com.hugo.springapiapp.domain.member.service.MemberService;
import com.hugo.springapiapp.global.error.ErrorCode;
import com.hugo.springapiapp.global.error.exception.AuthenticationException;
import com.hugo.springapiapp.global.jwt.cosntant.TokenType;
import com.hugo.springapiapp.global.jwt.service.TokenManager;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LogoutService {

    private final MemberService memberService;
    private final TokenManager tokenManager;

    @Transactional
    public void logout(String accessToken) {
        // 1. 토큰 검증
        tokenManager.validateToken(accessToken);

        // 2. 토큰 타입 확인 - 액세스 토큰인지
        Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
        String tokenType = tokenClaims.getSubject();

        if (!TokenType.isAccessToken(tokenType)) {
            throw new AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);
        }

        // 3. refreshToken 만료처리
        Long memberId = Long.valueOf((Integer) tokenClaims.get("memberId"));
        Member member = memberService.findMemberByMemberId(memberId);
        member.expireRefreshToken(LocalDateTime.now());
    }
}
