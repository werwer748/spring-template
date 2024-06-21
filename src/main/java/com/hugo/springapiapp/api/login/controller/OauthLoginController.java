package com.hugo.springapiapp.api.login.controller;

import com.hugo.springapiapp.api.login.dto.OauthLoginDto;
import com.hugo.springapiapp.api.login.service.OauthLoginService;
import com.hugo.springapiapp.api.login.validator.OauthValidator;
import com.hugo.springapiapp.domain.member.constant.MemberType;
import com.hugo.springapiapp.global.util.AuthorizationHeaderUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "authentication", description = "로그인/로그아웃/토큰재발급 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OauthLoginController {

    private final OauthValidator oauthValidator;
    private final OauthLoginService oauthLoginService;

//    @Tag(name = "authentication")
    @Operation(summary = "소셜 로그인 API", description = "소셜 로그인 API")
    @PostMapping("login")
    public ResponseEntity<OauthLoginDto.Response> oauthLogin(
            @RequestBody OauthLoginDto.Request oauthLoginRequestDto,
            HttpServletRequest httpServletRequest //? Header에 대한 정보 캐칭을 위해
    ) {
        String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);
        oauthValidator.validateMemberType(oauthLoginRequestDto.getMemberType());

        String accessToken = authorizationHeader.split(" ")[1];

        OauthLoginDto.Response response =
                oauthLoginService.oauthLogin(accessToken, MemberType.from(oauthLoginRequestDto.getMemberType()));

        return ResponseEntity.ok(response);
    }
}
