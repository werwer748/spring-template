package com.hugo.springapiapp.global.config;

import com.hugo.springapiapp.global.error.FeignClientExceptionErrorDecoder;
import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableFeignClients(basePackages = "com.hugo.springapiapp") //TODO 패키지명 수정
@Import(FeignClientsConfiguration.class)
public class FeignConfiguration {

    @Bean // feign 로깅 레밸 설정
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL; // 일단은 로그를 풀로 보도록
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignClientExceptionErrorDecoder();
    }

    @Bean
    public Retryer retryer() {
        //                          실행주기, 재시도를 위한 대기 시간의 최대값, 최대 재시도횟수 지정
        return new Retryer.Default(1000, 2000, 3);
    }
}
