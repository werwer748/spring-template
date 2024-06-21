package com.hugo.springapiapp.api.token.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class AccessTokenResponseDto {

    @Schema(description = "grantType", example = "Bearer", requiredMode = Schema.RequiredMode.REQUIRED)
    private String grantType;

    @Schema(
            description = "accessToken",
            example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBQ0NFU1MiLCJpYXQiOjE3MTg5NDM2MDgsImV4cCI6MTcxODk0NDUwOCwibWVtYmVySWQiOjEsInJvbGUiOiJBRE1JTiJ9._JAFA5j-_7Br-QZeX7tV2W8-6zRf5dyHU2P3GnzWAL6OiUYalLMsghr03ECKLRDY52D9g_xyZPg7llt37RKwOQ",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String accessToken;

    @Schema(description = "accessToken 만료시간", example = "2024-06-21 13:35:08", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date accessTokenExpireTime;

}
