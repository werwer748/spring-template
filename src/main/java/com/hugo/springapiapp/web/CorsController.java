package com.hugo.springapiapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CorsController { // cors 설정 테스트를 위해 cors.html 서빙

    @GetMapping("/cors") // 8082의 다른포트에서 열어보기
    public String cors() {
        return "cors";
    }
}
