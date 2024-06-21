package com.hugo.springapiapp.global.error;

import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Builder
public class ErrorResponse {

    private Integer errorCode;
    private String errorReason;
    private String errorMessage;

    public static ErrorResponse of(Integer errorCode, String errorReason, String errorMessage) {
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .errorReason(errorReason)
                .errorMessage(errorMessage)
                .build();
    }

    //? BindingResult: 요청값 검증시 validation에러가 BindingResult에 담기기 때문에 가져온다.
    public static ErrorResponse of(Integer errorCode, String errorReason, BindingResult bindingResult) {
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .errorReason(errorReason)
                .errorMessage(createErrorMessage(bindingResult))
                .build();
    }

    private static String createErrorMessage(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        // 에러를 구분하기 쉽게 만들기 위해서
        for (FieldError fieldError : fieldErrors) {
            if (!isFirst) {
                sb.append(", ");
            } else {
                isFirst = false;
            }
            sb.append("[");
            sb.append(fieldError.getField());
            sb.append("]");
            sb.append(fieldError.getDefaultMessage());
        }

        return sb.toString();
    }
}
