package com.hugo.springapiapp.api.token.service;

import com.hugo.springapiapp.api.token.dto.AccessTokenResponseDto;
import com.hugo.springapiapp.domain.member.entity.Member;
import com.hugo.springapiapp.domain.member.service.MemberService;
import com.hugo.springapiapp.global.jwt.cosntant.GrantType;
import com.hugo.springapiapp.global.jwt.service.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TokenService {

    private final MemberService memberService;
    private final TokenManager tokenManager;

    public AccessTokenResponseDto createAccessTokenByRefreshToken(String refreshToken) {
        Member member = memberService.findMemberByRefreshToken(refreshToken);

        Date accessTokenExpireTime = tokenManager.createAccessTokenExpireTime();
        String accessToken = tokenManager.createAccessToken(member.getMemberId(), member.getRole(), accessTokenExpireTime);

        return AccessTokenResponseDto.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .build();
    }
}
