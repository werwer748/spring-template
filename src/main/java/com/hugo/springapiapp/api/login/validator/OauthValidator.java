package com.hugo.springapiapp.api.login.validator;

import com.hugo.springapiapp.domain.member.constant.MemberType;
import com.hugo.springapiapp.global.error.ErrorCode;
import com.hugo.springapiapp.global.error.exception.AuthenticationException;
import com.hugo.springapiapp.global.error.exception.BusinessException;
import com.hugo.springapiapp.global.jwt.cosntant.GrantType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OauthValidator {

    public void validateMemberType(String memberType) {
        if (!MemberType.isMemberType(memberType)) {
            throw new BusinessException(ErrorCode.INVALID_MEMBER_TYPE);
        }
    }

}
