package com.hugo.springapiapp.global.resolver.memberInfo;

import io.swagger.v3.oas.annotations.Parameter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Parameter(hidden = true) // 오오오오오!!!
@Target(ElementType.PARAMETER) // 컨트롤러에 파라미터로 MemberInfo를 넣어줌 {} 사용하면 여러개 선언 가능
@Retention(RetentionPolicy.RUNTIME)
public @interface MemberInfo {
}
