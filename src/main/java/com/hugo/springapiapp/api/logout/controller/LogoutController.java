package com.hugo.springapiapp.api.logout.controller;

import com.hugo.springapiapp.api.logout.service.LogoutService;
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
public class LogoutController {

    private final LogoutService logoutService;

    @Operation(summary = "로그아웃 API", description = "로그아웃시 refresh token 만료 처리")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);

        String accessToken = authorizationHeader.split(" ")[1];
        logoutService.logout(accessToken);

        return ResponseEntity.ok("logout success");
    }
}
