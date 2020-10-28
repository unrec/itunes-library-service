package com.unrec.ituneslibrary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@Slf4j
@SpringBootApplication
@EnableCaching
public class ItunesLibraryApplication {

    public static void main(String[] args) {
        log.info("Version: {}", ItunesLibraryApplication.class.getPackage().getImplementationVersion());
        SpringApplication.run(ItunesLibraryApplication.class, args);
    }
}