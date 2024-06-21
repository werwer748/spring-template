package com.hugo.springapiapp.api.feigntest.client;

import com.hugo.springapiapp.api.health.dto.HealthCheckResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "http://localhost:8080", name = "helloClient") // name에는 Bean이름?
public interface HelloClient {

    @GetMapping(value = "/api/health", consumes = "application/json")
    HealthCheckResponseDto healthCheck();
}

