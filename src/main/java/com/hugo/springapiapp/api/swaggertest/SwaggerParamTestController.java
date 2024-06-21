package com.hugo.springapiapp.api.swaggertest;

import com.hugo.springapiapp.api.swaggertest.dto.QueryDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/swagger-test")
@RestController
public class SwaggerParamTestController {

//    @Parameters({ // 이렇게 작성하니까 쿼리파라미터 입력이 두개씩 떠서 Dto에 스키마로 써서 해결 @ParameterObject를 써주는거 빼먹지 말자
//            @Parameter(name = "queryA", description = "query paramA", in = ParameterIn.QUERY),
//            @Parameter(name = "queryB", description = "query paramB", in = ParameterIn.QUERY),
//            @Parameter(name = "variable", description = "path parma", in = ParameterIn.PATH, required = true)
//    })
    @Parameter(name = "variable", description = "path parma", in = ParameterIn.PATH, required = true)
    @GetMapping("/{variable}")
    public String swaggerTest(@ParameterObject QueryDto query, @PathVariable String variable) {
        log.info("query: {}, path variable: {}", query, variable);
        return "swagger test";
    }
}
