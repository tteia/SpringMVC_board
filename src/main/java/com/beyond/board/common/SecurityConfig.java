package com.beyond.board.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Spring security 설정을 customizing 하기 위함.
@EnableGlobalMethodSecurity(prePostEnabled = true) // pre : 사전 인증 검증, post : 사후 인증 검증.
public class SecurityConfig {
    @Bean
    public SecurityFilterChain myFilter(HttpSecurity httpSecurity) throws Exception {
        // 이 주석 위까지는 관례적으로 붙여주는 것. 우리는 builder 패턴을 활용해 원하는 것만 조립.
        return httpSecurity // builder 패턴이니까 원하는 것만 사용할 수 있다 !
                // csrf 공격에 대한 설정은 하지 않겠다는 의미.
                // csrf 공격 : 사용자가 자신의 의지와 무관하게 공격자가 의도한 행동을 해서 특정 웹 페이지를 보안에 취약하게 한다거나 수정, 삭제 등의 작업을 하게 만드는 공격 방법.
                // ex ) api 를 내가 보낸 요청처럼 처리해서 정보를 가져감.
                .csrf().disable()
                .authorizeRequests()
                    // antMatchers 는 인증 제외. (permitAll)
                    .antMatchers("/", "/author/register","/author/login-screen", "/image/**")
                    .permitAll()
                    // 그 외 요청은 모두 인증 필요.
                    .anyRequest().authenticated()
                // 세션 로그인
                .and()
                // 세션 로그인이 아니라 토큰 로그인일 경우
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .formLogin()
                    .loginPage("/author/login-screen")
                    // doLogin 메서드는 스프링에서 미리 구현.
                    .loginProcessingUrl("/doLogin")
                        // 하지만 doLogin 에 넘겨줄 email, password 속성명은 별도 지정 필요.
                        .usernameParameter("email")
                        .passwordParameter("password")
                .and()
                    // 마찬가지로 spring 에 내장 되어 있는 logout 활용.
                    .logout()
                    .logoutUrl("/doLogout") //security 에 구현된 doLogout 그대로 사용.
                .and()
                .build();
    }

}
