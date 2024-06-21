package com.hugo.springapiapp.global.util;

import com.hugo.springapiapp.global.error.ErrorCode;
import com.hugo.springapiapp.global.error.exception.AuthenticationException;
import com.hugo.springapiapp.global.jwt.cosntant.GrantType;
import org.springframework.util.StringUtils;

public class AuthorizationHeaderUtils {

    public static void validateAuthorization(String authorizationHeader) {

        // 1. authorizationHeader 필수 체크
        if (!StringUtils.hasText(authorizationHeader)) {
            throw new AuthenticationException(ErrorCode.NOT_EXISTS_AUTHORIZATION);
        }

        // 2. authorizationHeader Bearer 체크
        String[] authorizations = authorizationHeader.split(" "); // [Bearer, 토큰값]
        if (authorizations.length < 2 || (!GrantType.BEARER.getType().equals(authorizations[0]))) {
            throw new AuthenticationException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
        }

        // Bearer로 토큰 넘어온거 알았으니 여기서 토큰값만 뽑아서 넘겨도 되지 않을까...? => 나중에 혼자 테스트
    }

}
