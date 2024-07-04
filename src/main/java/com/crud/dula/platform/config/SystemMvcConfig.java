package com.crud.dula.platform.config;

import com.crud.dula.common.result.JacksonObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.List;

/**
 * web mvc 配置
 *
 * @author crud
 * @date 2023/10/11
 */
@EnableSwagger2WebMvc
@Configuration
public class SystemMvcConfig implements WebMvcConfigurer {

    @Value("${dula.api.online-file:true}")
    private Boolean onlineApiFile;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (onlineApiFile) {
            registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 移除默认的 MappingJackson2HttpMessageConverter
        converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter(new JacksonObjectMapper());
        converters.add(jackson2HttpMessageConverter);
    }
}
