package com.unrec.ituneslibrary.config;

import com.unrec.ituneslibrary.parser.dom.DomParser;
import com.unrec.ituneslibrary.service.LibraryDatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@Configuration
public class StartupDataLoaderConfig {

    @Autowired
    private DomParser parser;
    @Autowired
    private LibraryDatabaseService service;

    @Bean
    @Profile("!test")
    @ConditionalOnProperty(value = "itunes-library-service.startup-parsing", havingValue = "true")
    CommandLineRunner commandLineRunner() {
        return args -> {
            try {
                parser.setFile(new ClassPathResource("library.xml").getInputStream());
                service.preloadData();
                service.saveData();

                log.info("File-based library was successfully parsed on start-up.");
                log.info("Source library date: {}", parser.getLibrary().getDate());
                log.info("Total records: {}", parser.getTracks().size());
                log.info("Parsed data loaded to the database");
            } catch (Exception e) {
                log.warn("Failed to parse a file-based library on start-up: {}", e.getMessage());
            }
        };
    }
}