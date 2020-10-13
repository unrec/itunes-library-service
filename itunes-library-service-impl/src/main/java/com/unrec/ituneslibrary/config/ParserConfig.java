package com.unrec.ituneslibrary.config;

import com.unrec.ituneslibrary.parser.dom.DomParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@Configuration
public class ParserConfig {

    @Value("${itunes-library-service.startup-parsing::true}")
    private boolean isParsingEnabled;

    @Autowired
    private DomParser parser;

    @Bean
    @Profile("!test")
    CommandLineRunner commandLineRunner() {
        return args -> {
            if (isParsingEnabled) {
                try {
                    parser.setFile(new ClassPathResource("library.xml").getInputStream());
                    parser.parse();
                } catch (Exception e) {
                    log.warn("Failed to parse a file-based library on start-up: {}", e.getMessage());
                }
                log.info("File-based library was successfully parsed on start-up.");
                log.info("Source library date: {}", parser.getLibrary().getDate());
                log.info("Total trackRecords: {}", parser.getTracks().size());
            } else {
                log.info("Start-up parsing of the file-based library was disabled.");
            }
        };
    }
}