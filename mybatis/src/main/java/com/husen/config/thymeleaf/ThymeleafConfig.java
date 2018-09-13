package com.husen.config.thymeleaf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import javax.servlet.ServletContext;

/**
 * Thymeleaf 配置文件
 * Created by HuSen on 2018/7/3 14:22.
 */
@Configuration
public class ThymeleafConfig {
    private static final String PREFIX = "/WEB-INF/templates/";
    private static final String SUFFIX = ".html";
    private static final String TEMPLATE_MODE = "HTML";
    private static final Boolean CACHEABLE = false;
    private static final String CHARACTER_ENCODING = "UTF-8";

    @Bean
    public ServletContextTemplateResolver templateResolver(ServletContext servletContext){
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(servletContext);
        resolver.setPrefix(PREFIX);
        resolver.setSuffix(SUFFIX);
        resolver.setTemplateMode(TEMPLATE_MODE);
        resolver.setCacheable(CACHEABLE);
        resolver.setCharacterEncoding(CHARACTER_ENCODING);
        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(ServletContextTemplateResolver servletContextTemplateResolver){
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(servletContextTemplateResolver);
        return engine;
    }

    @Bean
    public ThymeleafViewResolver thymeleafViewResolver(SpringTemplateEngine springTemplateEngine){
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(springTemplateEngine);
        resolver.setCharacterEncoding(CHARACTER_ENCODING);
        return resolver;
    }
}
