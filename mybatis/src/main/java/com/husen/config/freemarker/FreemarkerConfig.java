//package com.husen.config.freemarker;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
//
///**
// * Created by HuSen on 2018/7/3 13:56.
// */
//@Configuration
//public class FreemarkerConfig {
//    private static final String TEMPLATE_LOADER_PATH = "/WEB-INF/ftl";
//    private static final String DEFAULT_ENCODING = "utf-8";
//    private static final Boolean CACHE = false;
//    private static final String PREFIX = "";
//    private static final String SUFFIX = ".ftl";
//    private static final String CONTENT_TYPE = "text/html;charset=UTF-8";
//    private static final Boolean ALLOW_SESSION_OVERRIDE = true;
//    private static final Boolean ALLOW_REQUEST_OVERRIDE = true;
//    private static final Boolean EXPOSE_SPRING_MACRO_HELPERS = true;
//    private static final Boolean EXPOSE_REQUEST_ATTRIBUTES = true;
//    private static final Boolean EXPOSE_SESSION_ATTRIBUTES = true;
//    private static final String REQUEST_CONTEXT_ATTRIBUTE = "request";
//
//    @Bean
//    public FreeMarkerConfig freeMarkerConfig(){
//        FreeMarkerConfig freeMarkerConfig = new FreeMarkerConfigurer();
//        ((FreeMarkerConfigurer) freeMarkerConfig).setTemplateLoaderPath(TEMPLATE_LOADER_PATH);
//        ((FreeMarkerConfigurer) freeMarkerConfig).setDefaultEncoding(DEFAULT_ENCODING);
//        return freeMarkerConfig;
//    }
//
//    @Bean
//    public FreeMarkerViewResolver freeMarkerViewResolver(){
//        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
//        resolver.setCache(CACHE);
//        resolver.setPrefix(PREFIX);
//        resolver.setSuffix(SUFFIX);
//        resolver.setContentType(CONTENT_TYPE);
//        resolver.setAllowSessionOverride(ALLOW_SESSION_OVERRIDE);
//        resolver.setAllowRequestOverride(ALLOW_REQUEST_OVERRIDE);
//        resolver.setExposeSpringMacroHelpers(EXPOSE_SPRING_MACRO_HELPERS);
//        resolver.setExposeRequestAttributes(EXPOSE_REQUEST_ATTRIBUTES);
//        resolver.setExposeSessionAttributes(EXPOSE_SESSION_ATTRIBUTES);
//        resolver.setRequestContextAttribute(REQUEST_CONTEXT_ATTRIBUTE);
//        return resolver;
//    }
//}
