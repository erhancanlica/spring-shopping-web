package com.mycompany.shopping.config;

import com.mycompany.shopping.utils.ProfileConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
@Profile({ ProfileConstants.SPRING_PROFILE_PRODUCTION })
public class StaticResourcesWebConfiguration implements WebMvcConfigurer {

    protected static final String[] RESOURCE_LOCATIONS = new String[] {
            "classpath:/static/",
            "classpath:/static/content/",
            "classpath:/static/i18n",
    };

    protected static final String[] RESOURCE_PATHS = new String[] {
            "/*.js",
            "/*.css",
            "/*.svg",
            "/*.png",
            "*.ico",
            "/content/**",
            "/i18n/*",
    };

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ResourceHandlerRegistration resourceHandlerRegistration = appendResourceHandler(registry);
        initializeResourceHandler(resourceHandlerRegistration);
    }

    private void initializeResourceHandler(ResourceHandlerRegistration resourceHandlerRegistration) {
        resourceHandlerRegistration.addResourceLocations(RESOURCE_LOCATIONS).setCacheControl(getCacheControl());
    }

    private CacheControl getCacheControl() {
        return CacheControl.maxAge(1461, TimeUnit.DAYS).cachePublic();
    }

    private ResourceHandlerRegistration appendResourceHandler(ResourceHandlerRegistry registry) {
        return registry.addResourceHandler(RESOURCE_LOCATIONS);
    }
}
