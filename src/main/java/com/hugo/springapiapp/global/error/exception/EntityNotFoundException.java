package com.hugo.springapiapp.global.error.exception;

import com.hugo.springapiapp.global.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
