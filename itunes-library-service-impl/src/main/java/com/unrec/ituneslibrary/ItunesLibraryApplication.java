package com.unrec.ituneslibrary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ItunesLibraryApplication {

    public static void main(String[] args) {
        log.info("Version: {}", ItunesLibraryApplication.class.getPackage().getImplementationVersion());
        SpringApplication.run(ItunesLibraryApplication.class, args);
    }
}