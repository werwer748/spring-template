package com.hugo.springapiapp.api.login.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hugo.springapiapp.global.jwt.dto.JwtTokenDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

public class OauthLoginDto {

    @Getter @Setter
    public static class Request {
        @Schema(description = "소셜 로그인 회원 타입", example = "KAKAO", requiredMode = Schema.RequiredMode.REQUIRED)
        private String memberType;
    }

    @Getter @Setter @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class Response {
        @Schema(description = "grantType", example = "Bearer", requiredMode = Schema.RequiredMode.REQUIRED)
        private String grantType;

        @Schema(
                description = "accessToken",
                example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBQ0NFU1MiLCJpYXQiOjE3MTg5NDM2MDgsImV4cCI6MTcxODk0NDUwOCwibWVtYmVySWQiOjEsInJvbGUiOiJBRE1JTiJ9._JAFA5j-_7Br-QZeX7tV2W8-6zRf5dyHU2P3GnzWAL6OiUYalLMsghr03ECKLRDY52D9g_xyZPg7llt37RKwOQ",
                requiredMode = Schema.RequiredMode.REQUIRED)
        private String accessToken;

        @Schema(description = "accessToken 만료시간", example = "2024-06-21 13:35:08", requiredMode = Schema.RequiredMode.REQUIRED)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Date accessTokenExpireTime;

        @Schema(
                description = "refreshToken",
                example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJSRUZSRVNIIiwiaWF0IjoxNzE4OTQzNjA4LCJleHAiOjE3MjAxNTMyMDgsIm1lbWJlcklkIjoxfQ.hIjLcdTjSjmgtocjJ4k12GAwI5KCQTQkofL9_o2689BmTMmL6QkqVu6UOIxhLZaOTFuS2tryI0Xkbay8bGZGJQ",
                requiredMode = Schema.RequiredMode.REQUIRED)
        private String refreshToken;
        @Schema(description = "refreshToken 만료시간", example = "2024-06-21 13:35:08", requiredMode = Schema.RequiredMode.REQUIRED)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Date refreshTokenExpireTime;

        // 변환로직은 Dto에서 처리하면 서비스가 깔끔해진다~
        public static Response of(JwtTokenDto jwtTokenDto) {
            return Response.builder()
                    .grantType(jwtTokenDto.getGrantType())
                    .accessToken(jwtTokenDto.getAccessToken())
                    .accessTokenExpireTime(jwtTokenDto.getAccessTokenExpireTime())
                    .refreshToken(jwtTokenDto.getRefreshToken())
                    .refreshTokenExpireTime(jwtTokenDto.getRefreshTokenExpireTime())
                    .build();
        }
    }
}
