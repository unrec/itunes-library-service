package com.ituneslibrary.feign;

import feign.Feign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
@ConditionalOnClass(Feign.class)
@EnableFeignClients(basePackageClasses = TrackResourceClient.class)
@ConditionalOnMissingBean(type = "com.unrec.ituneslibrary.ItunesLibraryApplication")
public class FeignAutoConfig {

    static final String SERVICE_URL = "http://${itunes-library-service.host:itunes-library-service}:${itunes-library-service.port:80}";
    static final String SERVICE_FEIGN_NAME = "itunes-library-service";
}