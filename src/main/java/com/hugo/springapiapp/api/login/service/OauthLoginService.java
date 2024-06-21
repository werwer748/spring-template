package com.hugo.springapiapp.api.login.service;

import com.hugo.springapiapp.api.login.dto.OauthLoginDto;
import com.hugo.springapiapp.domain.member.constant.MemberType;
import com.hugo.springapiapp.domain.member.constant.Role;
import com.hugo.springapiapp.domain.member.entity.Member;
import com.hugo.springapiapp.domain.member.service.MemberService;
import com.hugo.springapiapp.external.oauth.model.OAuthAttributes;
import com.hugo.springapiapp.external.oauth.service.SocialLoginApiService;
import com.hugo.springapiapp.external.oauth.service.SocialLoginApiServiceFactory;
import com.hugo.springapiapp.global.jwt.dto.JwtTokenDto;
import com.hugo.springapiapp.global.jwt.service.TokenManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OauthLoginService {

    private final MemberService memberService;
    private final TokenManager tokenManager;

    @Transactional
    public OauthLoginDto.Response oauthLogin(String accessToken, MemberType memberType) {
        SocialLoginApiService socialLoginApiService = SocialLoginApiServiceFactory.getSocialLoginApiService(memberType);
        OAuthAttributes userInfo = socialLoginApiService.getUserInfo(accessToken);
        log.info("userInfo: {}", userInfo);

        JwtTokenDto jwtTokenDto;

        Optional<Member> optionalMember = memberService.findMemberByEmail(userInfo.getEmail());

        if (optionalMember.isEmpty()) { // 가입된 회원이 아니라서 신규 회원 가입
            Member oauthMember = userInfo.toMemberEntity(memberType, Role.ADMIN); // 어드민 가입 테스트용
//            Member oauthMember = userInfo.toMemberEntity(memberType, Role.USER); // 유저가입 테스트용
            oauthMember = memberService.registerMember(oauthMember);

            // 토큰 생성
            jwtTokenDto = tokenManager.createJwtTokenDto(oauthMember.getMemberId(), oauthMember.getRole());
            oauthMember.updateRefreshToken(jwtTokenDto);
        } else { // 기존 회원인 경우
            Member oauthMember = optionalMember.get();

            // 토큰 생성
            jwtTokenDto = tokenManager.createJwtTokenDto(oauthMember.getMemberId(), oauthMember.getRole());
            oauthMember.updateRefreshToken(jwtTokenDto);
        }

        return OauthLoginDto.Response.of(jwtTokenDto);
    }
}
