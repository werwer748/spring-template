package com.hugo.springapiapp.global.error;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class FeignClientExceptionErrorDecoder implements ErrorDecoder {

    // 만들어져있는걸 사용하는데 기본적으로 에러 디코더를 구현하고 있다.
    private ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("{} 요청 실패, status : {}, reason : {}", methodKey, response.status(), response.reason());
        FeignException exception = FeignException.errorStatus(methodKey, response);
        HttpStatus httpStatus = HttpStatus.valueOf(response.status());

        // 500번대 에러면 재시도
        if (httpStatus.is5xxServerError()) {
            return new RetryableException(
                    response.status(),
                    exception.getMessage(),
                    response.request().httpMethod(),
                    exception.getCause(),
                    (Long) null,
                    response.request()
            );
        }

        return errorDecoder.decode(methodKey, response);
    }

}
