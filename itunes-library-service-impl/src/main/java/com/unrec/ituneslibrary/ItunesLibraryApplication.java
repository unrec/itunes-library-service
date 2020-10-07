package com.unrec.ituneslibrary;

import com.unrec.ituneslibrary.parser.dom.DomParser;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
public class ItunesLibraryApplication {

    @Autowired
    private DomParser parser;

    public static void main(String[] args) {
        log.info("Version: {}", ItunesLibraryApplication.class.getPackage().getImplementationVersion());
        SpringApplication.run(ItunesLibraryApplication.class, args);
    }

    @Bean
    @Profile("!test")
    CommandLineRunner commandLineRunner() {
        return args -> {
            parser.setFile(new ClassPathResource("/library.xml").getInputStream());
            try {
                parser.parse();
            } catch (DocumentException e) {
                log.warn("Fail to parse a library XML file on start-up: {}", e.getMessage());
            }
            log.info("Library successfully parsed on start-up.");
            log.info("Source library saved at: {}", parser.getLibrary().getDate());
            log.info("Total tracks: {}", parser.getTracks().size());
        };
    }
}