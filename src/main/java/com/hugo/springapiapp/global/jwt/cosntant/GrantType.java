package com.hugo.springapiapp.global.jwt.cosntant;

import lombok.Getter;

@Getter
public enum GrantType {

    BEARER("Bearer");

    GrantType(String type) {
        this.type = type;
    }

    private final String type;
}
