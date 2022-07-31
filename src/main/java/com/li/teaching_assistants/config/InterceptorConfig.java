package com.li.teaching_assistants.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ///images/** images 为相对路径 即resources/static 目录下的静态资源 images为存放图片的目录
        //file:C:\Users\11455\Desktop\teaching_assistants\src\main\resources\static\images\ 该路径为绝对路径
        registry.addResourceHandler("/images/**").addResourceLocations("file:C:\\teaching_assistants\\images\\");
    }
}
