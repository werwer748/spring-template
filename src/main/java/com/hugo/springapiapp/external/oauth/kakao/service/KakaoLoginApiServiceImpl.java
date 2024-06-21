package com.hugo.springapiapp.external.oauth.kakao.service;

import com.hugo.springapiapp.domain.member.constant.MemberType;
import com.hugo.springapiapp.external.oauth.kakao.client.KakaoUserInfoClient;
import com.hugo.springapiapp.external.oauth.kakao.dto.KakaoUserInfoResponseDto;
import com.hugo.springapiapp.external.oauth.model.OAuthAttributes;
import com.hugo.springapiapp.external.oauth.service.SocialLoginApiService;
import com.hugo.springapiapp.global.jwt.cosntant.GrantType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class KakaoLoginApiServiceImpl implements SocialLoginApiService {

    private final KakaoUserInfoClient kakaoUserInfoClient;

    @Override
    public OAuthAttributes getUserInfo(String accessToken) {
        String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";
        KakaoUserInfoResponseDto kakaoUserInfoResponseDto = kakaoUserInfoClient.getKakaoUserInfo(
                CONTENT_TYPE,
                GrantType.BEARER.getType() + " " + accessToken
        );

        KakaoUserInfoResponseDto.KakaoAccount kakaoAccount = kakaoUserInfoResponseDto.getKakaoAccount();
        String email = kakaoAccount.getEmail();

        return OAuthAttributes.builder()
                .email(!StringUtils.hasText(email) ? kakaoUserInfoResponseDto.getId() : email)
                .name(kakaoAccount.getProfile().getNickname())
                .profile(kakaoAccount.getProfile().getThumbnailImageUrl())
                .memberType(MemberType.KAKAO)
                .build();
    }
}
