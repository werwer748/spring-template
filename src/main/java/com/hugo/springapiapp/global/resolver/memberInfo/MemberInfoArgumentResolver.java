package com.hugo.springapiapp.global.resolver.memberInfo;

import com.hugo.springapiapp.domain.member.constant.Role;
import com.hugo.springapiapp.global.jwt.service.TokenManager;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component // 등록이 필요함
@RequiredArgsConstructor
public class MemberInfoArgumentResolver implements HandlerMethodArgumentResolver {

    private final TokenManager tokenManager;

    @Override
    public boolean supportsParameter(MethodParameter parameter) { // return true면 resolveArgument 실행 아니면 넘어감

        // MemberInfo 어노테이션이 있는지 확인!
        boolean hasMemberInfoAnnotation = parameter.hasParameterAnnotation(MemberInfo.class);

        // 넘어온 파라미터가 MemberInfoDto거나 MemberInfoDto를 상속받았는지 확인
        boolean hasMemberInfoDto = MemberInfoDto.class.isAssignableFrom(parameter.getParameterType());
        return hasMemberInfoAnnotation && hasMemberInfoDto;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // token에 있던 정보를 MemberInfoDto로 만들어서 반환을 해준다.
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest(); // HttpServletRequest 가져오기
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = authorizationHeader.split(" ")[1];

        Claims tokenClaims = tokenManager.getTokenClaims(token);
        Long memberId = Long.valueOf((Integer) tokenClaims.get("memberId"));
        String role = (String) tokenClaims.get("role");

        return MemberInfoDto.builder()
                .memberId(memberId)
                .role(Role.from(role)) // 스트링이기 떄문에 변환 필요
                .build();
    }
}
