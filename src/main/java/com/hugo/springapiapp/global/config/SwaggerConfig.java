package com.hugo.springapiapp.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
    private static final String SECURITY_SCHEMA_NAME = "Authorization";

    @Bean
    public OpenAPI customOpenApi() { // 이 설정에 핵심이 되는 로직
        return new OpenAPI()
                .components(new Components() // Authorize 버튼 만들기
                        .addSecuritySchemes(SECURITY_SCHEMA_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEMA_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEMA_NAME))
                .info(
                        new Info()
                                .title("spring-api-app")
                                .version("1.0")
                                .description("스웨거 만들어보기~~")
                )
                ;
    }
}
