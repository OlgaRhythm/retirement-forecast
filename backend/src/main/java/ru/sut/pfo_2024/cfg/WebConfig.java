package ru.sut.pfo_2024.cfg;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.util.unit.DataSize;


/**
 * @author iegorov
 * @since 1.0.0
 */
@Configuration
public class WebConfig {

//    private MultipartConfigFactory factory = new MultipartConfigFactory();
//
//    @Bean
//    public MultipartConfigElement multipartConfigElement() {
//        factory.setMaxFileSize(DataSize.ofMegabytes(10L));
//        factory.setMaxRequestSize(DataSize.ofMegabytes(10L));
//        return factory.createMultipartConfig();
//    }
//
//    @Bean
//    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
//        return factory -> factory.setContextPath("/");
//    }
}