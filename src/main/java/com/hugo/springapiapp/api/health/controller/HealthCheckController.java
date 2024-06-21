package com.hugo.springapiapp.api.health.controller;

import com.hugo.springapiapp.api.health.dto.HealthCheckResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@Tag(name = "health check", description = "서버 상태 체크")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HealthCheckController {

    /*
    * org.springframework.core.env.Environment
    * 스프링에서 현재 앱이 어떤 설정을 사용중인지 알려줌
    * Like process.NODE_ENV??
    */
    private final Environment environment;

    @Tag(name = "health check")
    @Operation(summary = "서버 Health Check API", description = "현재 서버가 정상적으로 기동이 된 상태인지 검사하는 API")
    @GetMapping("/health")
    public ResponseEntity<HealthCheckResponseDto> healthCheck() {

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        HealthCheckResponseDto healthCheckResponseDto = HealthCheckResponseDto.builder()
                .health("ok")
                .activeProfiles(Arrays.asList(environment.getActiveProfiles())) // environment.getActiveProfiles() 현재 실행중인 properties가 무엇인지 알려준다.
                .build();

        return ResponseEntity.ok(healthCheckResponseDto); // 200~
    }
}
