package com.hugo.springapiapp.external.oauth.service;

import com.hugo.springapiapp.domain.member.constant.MemberType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class SocialLoginApiServiceFactory {

    // 각각의 소셜 로그인 구현체가 담기도록
    private static Map<String, SocialLoginApiService> socialLoginApiServices;

    public SocialLoginApiServiceFactory(Map<String, SocialLoginApiService> socialLoginApiServices) {
        this.socialLoginApiServices = socialLoginApiServices;
    }

    public static SocialLoginApiService getSocialLoginApiService(MemberType memberType) { // 필요한 구현체를 꺼내서 사용
        String socialLoginApiServiceBeanName = "";

        if (MemberType.KAKAO.equals(memberType)) {
            socialLoginApiServiceBeanName = "kakaoLoginApiServiceImpl"; // kakaoLoginServiceImpl
        }

        return socialLoginApiServices.get(socialLoginApiServiceBeanName);
    }
}
