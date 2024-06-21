package com.hugo.springapiapp.api.token.controller;

import com.hugo.springapiapp.api.token.dto.AccessTokenResponseDto;
import com.hugo.springapiapp.api.token.service.TokenService;
import com.hugo.springapiapp.global.util.AuthorizationHeaderUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "authentication")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TokenController {

    private final TokenService tokenService;

    @Operation(summary = "accessToken 재발급 API", description = "accessToken 재발급 API")
    @PostMapping("/access-token/issue")
    public ResponseEntity<AccessTokenResponseDto> createAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);

        String refreshToken = authorizationHeader.split(" ")[1];
        AccessTokenResponseDto accessTokenResponseDto = tokenService.createAccessTokenByRefreshToken(refreshToken);

        return ResponseEntity.ok(accessTokenResponseDto);
    }
}
