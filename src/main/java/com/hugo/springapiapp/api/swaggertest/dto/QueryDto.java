package com.hugo.springapiapp.api.swaggertest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryDto {
    @Schema(description = "query paramA")
    private String queryA;

    @Schema(description = "query paramB")
    private Integer queryB;
}
