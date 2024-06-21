package com.hugo.springapiapp.global.config.web.kakaotoken.client;

import com.hugo.springapiapp.global.config.web.kakaotoken.dto.KakaoTokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "https://kauth.kakao.com", name = "kakaoTokenClient")
public interface KakaoTokenClient {

    @PostMapping(value = "/oauth/token", consumes = "application/json")
//    KakaoTokenDto.Response requestKakaoToken(@RequestHeader("Content-Type") String contentType);
    KakaoTokenDto.Response requestKakaoToken(
            @RequestHeader(HttpHeaders.CONTENT_TYPE) String contentType,
            @SpringQueryMap KakaoTokenDto.Request request
    );
}
