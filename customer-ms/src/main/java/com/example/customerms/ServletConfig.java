package com.example.customerms;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfig {

    @Bean
    public ServletRegistrationBean<TestServlet> testServlet() {
        return new ServletRegistrationBean<>(
                new TestServlet(),
                "/test-servlet"
        );
    }
}
