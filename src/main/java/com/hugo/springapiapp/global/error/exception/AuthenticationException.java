package com.hugo.springapiapp.global.error.exception;

import com.hugo.springapiapp.global.error.ErrorCode;

public class AuthenticationException extends BusinessException {

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode); // 부모 생서앚 호출
    }
}
