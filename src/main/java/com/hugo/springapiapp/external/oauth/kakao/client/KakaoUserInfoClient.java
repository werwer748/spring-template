package com.hugo.springapiapp.external.oauth.kakao.client;

import com.hugo.springapiapp.external.oauth.kakao.dto.KakaoUserInfoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "https://kapi.kakao.com", name = "kakaoUserInfoClient")
public interface KakaoUserInfoClient {

    @GetMapping(value = "/v2/user/me", consumes = "application/json")
    KakaoUserInfoResponseDto getKakaoUserInfo(
            @RequestHeader(HttpHeaders.CONTENT_TYPE) String contentType,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken
    );
}
