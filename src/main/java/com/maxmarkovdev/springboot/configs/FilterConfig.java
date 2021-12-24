package com.maxmarkovdev.springboot.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
public class FilterConfig {
    @Bean
    public HiddenHttpMethodFilter someFilterRegistration() {
        return new HiddenHttpMethodFilter();
    }
}
