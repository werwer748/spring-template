package com.hugo.springapiapp.global.resolver.memberInfo;

import com.hugo.springapiapp.domain.member.constant.Role;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class MemberInfoDto { // 토큰에 있는 정보를 담아서 어노테이션으로 활용

    private Long memberId;
    private Role role;

}
