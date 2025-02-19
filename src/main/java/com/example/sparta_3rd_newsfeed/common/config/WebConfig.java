package com.example.sparta_3rd_newsfeed.common.config;

import com.example.sparta_3rd_newsfeed.common.filter.AuthFilter;
import com.example.sparta_3rd_newsfeed.common.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class WebConfig {

    // AuthFilter를 먼저 실행
    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthFilter()); // AuthFilter 등록
        registrationBean.setOrder(0); // AuthFilter가 먼저 실행되도록 설정
        registrationBean.addUrlPatterns("/members/signup", "/members/login");  // 회원가입, 로그인 경로만 필터 적용
        return registrationBean;
    }

    // LoginFilter는 회원가입 및 로그인 경로를 제외하고 전체 경로에 대해 필터링
    @Bean
    public FilterRegistrationBean<LoginFilter> loginFilter() {
        FilterRegistrationBean<LoginFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoginFilter()); // LoginFilter 등록
        registrationBean.setOrder(1); // AuthFilter가 먼저 실행되도록 설정
        registrationBean.addUrlPatterns("/members/*");  // /members/* 경로에 대해서만 필터 적용
        return registrationBean;
    }
}

