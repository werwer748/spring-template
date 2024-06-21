package com.hugo.springapiapp.api.member.dto;

import com.hugo.springapiapp.domain.member.constant.Role;
import com.hugo.springapiapp.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class MemberInfoResponseDto {

    @Schema(description = "회원 아이디", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long memberId;

    @Schema(description = "이메일", example = "test@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "회원명", example = "홍길동", requiredMode = Schema.RequiredMode.REQUIRED)
    private String memberName;

    @Schema(description = "프로필 이미지 경로", example = "http://image.url", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String profile;

    @Schema(description = "회원 역할", example = "USER", requiredMode = Schema.RequiredMode.REQUIRED)
    private Role role;

    public static MemberInfoResponseDto of(Member member) {
        return MemberInfoResponseDto.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .memberName(member.getMemberName())
                .profile(member.getProfile())
                .role(member.getRole())
                .build();
    }
}