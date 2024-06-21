package com.hugo.springapiapp.domain.member.constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MemberType {
    KAKAO;

    public static MemberType from(String type) {
        return MemberType.valueOf(type.toUpperCase());
    }

    public static boolean isMemberType(String type) {
        List<MemberType> memberTypes = Arrays.stream(MemberType.values()) // 넘어온 타입과 일치하는 값만 리스트로 리턴
                .filter(memberType -> memberType.name().equals(type))
                .toList();
        return !memberTypes.isEmpty();
    }
}
