package com.hugo.springapiapp.global.config.web;

import com.hugo.springapiapp.global.intercepter.AdminAuthorizationInterceptor;
import com.hugo.springapiapp.global.intercepter.AuthenticationInterceptor;
import com.hugo.springapiapp.global.resolver.memberInfo.MemberInfoArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;
    private final AdminAuthorizationInterceptor adminAuthorizationInterceptor;

    private final MemberInfoArgumentResolver memberInfoArgumentResolver;

    /*
    * SOP (Same Origin Policy) : 어떤 출처에서 다른 출처의 리소스를 사용하는 것을 제한하는 보안 방식
    * CORS (Cross-Origin Resource Sharing) : 다른 출처에 리소스를 공유하는 것
    */

    /*
    * Preflight Request (사전 요청)
    *
    * 웹브라우저는 기본적으로 cross origin에 대해서 HTTP 요청 전에 서버 측에서 해당 요청을 보낼 수 있는지 확인하는
    * Preflight Request(사전 요청)을 보냄
    *
    * HTTP OPTIONS 메소드를 사용
    *
    * Preflight Request를 사용하는 이유는 CORS 오류는 웹브라우저에서 발생하기 때문에
    * 서버에서는 정상적으로 요청을 처리했는데 클라이언트에서는 오류가 난 것처럼 보일 수 있기 때문에
    * Preflight Request를 이용하여 사전에 확인함
    *
    * 단! 모든 요청이 Preflight를 발생시키지는 않는다. 단순요청(Simple Requests)에는 Preflight가 발생하지 않음
    * 아래의 모든 조건을 만족하는 경우를 Simple Requests라 함
    *
    * 1. Http Method가 GET, HEAD, POST 중 1
    * 2. 수동으로 설정한 헤더가 “Accept”, Accept-Language”, “Content-Language”, “Content-Type” 중 1
    * 3. 단, Content-Type 헤더는 “application/x-www-form-urlencoded”, “multipart/form-data”, “text/plain” 중 1
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // 어떤 url로 요청이 왔을때 cors를 허용할 것인지.
                .allowedOrigins("http://localhost:8082") // 어떤 Origin을 허용할 것인지
                .allowedMethods( // 어떤 메소드를 허용하는지
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.OPTIONS.name()
                )
                // 계속 Preflight + 본요청이 2번씩 날아오면 성능 떨어져서 브라우져에서 프리플라이트 요청을 캐싱하고 얼마동안 요청을 안하게끔되있는데
                // 그 시간을 서버에서 응답으로 바꿔줄 수 있음
                .maxAge(3600);
    }

    @Override  // interceptor 등록!
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(authenticationInterceptor)
                .order(1) //? order: 인터셉터를 여러개 등록할 경우 순서를 지정할 수 있다.
                .addPathPatterns("/api/**") //? addPathPatterns: 이 인터셉터가 어디서 동작할지 지정할 수 있다.
                .excludePathPatterns( //? excludePathPatterns: 인증 인터셉터에서 제외시킬 uri를 지정할 수 있다.
                        "/api/oauth/login",
                        "/api/access-token/issue",
                        "/api/logout",
                        "/api/health"
                );

        registry // 인터셉터를 추가할때는 이런식으로!
                .addInterceptor(adminAuthorizationInterceptor)
                .order(2)
                .addPathPatterns("/api/admin/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberInfoArgumentResolver); // ArgumentResolver 등록
    }
}
