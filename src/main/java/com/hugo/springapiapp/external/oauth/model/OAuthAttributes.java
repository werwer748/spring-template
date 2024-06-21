package com.hugo.springapiapp.external.oauth.model;

import com.hugo.springapiapp.domain.member.constant.MemberType;
import com.hugo.springapiapp.domain.member.constant.Role;
import com.hugo.springapiapp.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthAttributes {

    private String name;
    private String email;
    private String profile;
    private MemberType memberType;

    public Member toMemberEntity(MemberType memberType, Role role) {
        return Member.builder()
                .memberName(name)
                .email(email)
                .memberType(memberType)
                .profile(profile)
                .role(role)
                .build();
    }
}
