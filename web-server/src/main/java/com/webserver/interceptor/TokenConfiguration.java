//package com.vivedu.cloud.interceptor;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//@Configuration
//public class TokenConfiguration extends WebMvcConfigurationSupport {
//
//    // 这个方法才能在拦截器中自动注入查询数据库的对象
//    @Bean
//    TokenInterceptor tokenInterceptor() {
//        return new TokenInterceptor();
//
//    }
//
//    //配置生成器：添加一个拦截器，拦截路径为API以后的路径
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(tokenInterceptor()).addPathPatterns("/users/**");
//    }
//}
