package com.mycompany.shopping.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Configuration
public class WebConfiguration implements ServletContextInitializer {

    private final Logger log = LoggerFactory.getLogger(WebConfiguration.class);

    private final Environment environment;

    public WebConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if (environment.getActiveProfiles().length != 0) {
            log.info("Web application configuration, using profiles: {}", (Object[]) environment.getActiveProfiles());
        }

        log.info("Web application fully configured");
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();

        if (!CollectionUtils.isEmpty(configuration.getAllowedOrigins()) ||
                !CollectionUtils.isEmpty(configuration.getAllowedOriginPatterns())) {
            log.debug("Registering CORS filter");
            source.registerCorsConfiguration("/api/**", configuration);
            source.registerCorsConfiguration("/management/**", configuration);
            source.registerCorsConfiguration("/v2/api-docs", configuration);
            source.registerCorsConfiguration("/v3/api-docs", configuration);
            source.registerCorsConfiguration("/swagger-resources", configuration);
            source.registerCorsConfiguration("/swagger-ui/**", configuration);
        }
        return new CorsFilter(source);
    }
}
