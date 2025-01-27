package com.hugo.springapiapp.global.jwt.service;

import com.hugo.springapiapp.domain.member.constant.Role;
import com.hugo.springapiapp.global.error.ErrorCode;
import com.hugo.springapiapp.global.error.exception.AuthenticationException;
import com.hugo.springapiapp.global.jwt.cosntant.GrantType;
import com.hugo.springapiapp.global.jwt.cosntant.TokenType;
import com.hugo.springapiapp.global.jwt.dto.JwtTokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class TokenManager {

    private final String accessTokenExpirationTime;
    private final String refreshTokenExpirationTime;
    private final String tokenSecret;

    public JwtTokenDto createJwtTokenDto(Long memberId, Role role) {
        Date accessTokenExpireTime = createAccessTokenExpireTime();
        Date refreshTokenExpireTime = createRefreshTokenExpireTime();

        String accessToken = createAccessToken(memberId, role, accessTokenExpireTime);
        String refreshToken = createRefreshToken(memberId, refreshTokenExpireTime);

        return JwtTokenDto.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .refreshToken(refreshToken)
                .refreshTokenExpireTime(refreshTokenExpireTime)
                .build();
    }

    public Date createAccessTokenExpireTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpirationTime));
    }

    public Date createRefreshTokenExpireTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(refreshTokenExpirationTime));
    }

    public String createAccessToken(Long memberId, Role role, Date expirationTime) {

        return Jwts.builder()
                .setSubject(TokenType.ACCESS.name()) // 토큰 제목
                .setIssuedAt(new Date()) // 토큰 발급 시간
                .setExpiration(expirationTime) // 토큰 만료 시간
                .claim("memberId", memberId) // 회원 아이디
                .claim("role", role) // 유저 role
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))
                .setHeaderParam("typ", "JWT")
                .compact();
    }

    public String createRefreshToken(Long memberId, Date expirationTime) {

        return Jwts.builder()
                .setSubject(TokenType.REFRESH.name()) // 토큰 제목
                .setIssuedAt(new Date()) // 토큰 발급 시간
                .setExpiration(expirationTime) // 토큰 만료 시간
                .claim("memberId", memberId) // 회원 아이디
//                .claim("role", role) // 액세스 토큰보다 정보량을 줄이기 위해 받지 않음
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))
                .setHeaderParam("typ", "JWT")
                .compact();
    }

    // 토큰 검증
    public void validateToken(String token) {
        try {
//            Jwts.parser().setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token); // => deprecated
            Jwts.parserBuilder()
                    .setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8)) // 서명 키 설정
                    .build()
                    .parseClaimsJws(token); // JWT 파싱 및 검증

        } catch (ExpiredJwtException e) {
            log.info("token 만료", e);
            throw new AuthenticationException(ErrorCode.TOKEN_EXPIRED);
        } catch (Exception e) {
            log.info("유효하지 않은 token", e);
            throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
        }
    }

    // 토큰 페이로드의 클레임 정보 가져오기
    public Claims getTokenClaims(String token) {
        Claims claims = null;

        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8)) // 서명 키 설정
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.info("유효하지 않은 token", e);
            throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
        }

        return claims;
    }
}
