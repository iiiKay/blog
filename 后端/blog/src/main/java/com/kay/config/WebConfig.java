package com.kay.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * author: Kay
 * 项目名: blog
 * 开发日期:2023/5/6
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //设置静态资源映射
//        registry.addResourceHandler("/images/**").addResourceLocations("file:/D://uploadFile/");
        registry.addResourceHandler("/images/**").addResourceLocations("file:/www/wwwroot/images/");
    }
}
