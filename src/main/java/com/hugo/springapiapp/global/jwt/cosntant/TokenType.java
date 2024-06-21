package com.hugo.springapiapp.global.jwt.cosntant;

public enum TokenType {

    ACCESS, REFRESH;

    public static boolean isAccessToken(String tokenType) {
        return TokenType.ACCESS.name().equals(tokenType); // 액세스토큰이면 true
    }
}
