package com.hugo.springapiapp.api.member.controller;

import com.hugo.springapiapp.api.member.dto.MemberInfoResponseDto;
import com.hugo.springapiapp.api.member.service.MemberInfoService;
import com.hugo.springapiapp.global.jwt.service.TokenManager;
import com.hugo.springapiapp.global.resolver.memberInfo.MemberInfo;
import com.hugo.springapiapp.global.resolver.memberInfo.MemberInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "member", description = "회원 API")
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberInfoController {

    private final TokenManager tokenManager;
    private final MemberInfoService memberInfoService;

    @Operation(summary = "회원 정보 조회 API", description = "회원 정보 조회~")
    @ApiResponses({
            @ApiResponse(responseCode = "default"),
            @ApiResponse(responseCode="500", description = "서버 오류 발생", useReturnTypeSchema = false)
    })
    @GetMapping("/info")
    public ResponseEntity<MemberInfoResponseDto> getMemberInfo(
//            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader // MemberInfoArgumentResolver 등록전
            @MemberInfo MemberInfoDto memberInfoDto
            ) {
        Long memberId = memberInfoDto.getMemberId();
        MemberInfoResponseDto memberInfoResponseDto = memberInfoService.getMemberInfo(memberId);

        return ResponseEntity.ok(memberInfoResponseDto);
    }
}
