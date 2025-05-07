package org.zhj.agentz.infrastructure.config;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 * @Author 86155
 * @Date 2025/5/7
 */
@Configuration
public class CorsConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter>corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许所有来源
        corsConfiguration.addAllowedOriginPattern("*");
        // 允许携带认证信息
        corsConfiguration.setAllowCredentials(true);
        // 允许所有请求方法
        corsConfiguration.addAllowedMethod("*");
        // 允许所有请求头
        corsConfiguration.addAllowedHeader("*");
        // 预检请求有效期(秒)
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        // 设置过滤器优先级最高
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
