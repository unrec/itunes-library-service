package com.unrec.ituneslibrary;

import com.unrec.ituneslibrary.parser.dom.DomParser;
import com.unrec.ituneslibrary.repository.AlbumRepository;
import com.unrec.ituneslibrary.repository.ArtistRepository;
import com.unrec.ituneslibrary.repository.PlaylistRepository;
import com.unrec.ituneslibrary.repository.TrackRepository;
import com.unrec.ituneslibrary.service.LibraryDatabaseService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.support.TransactionTemplate;
import java.util.function.Supplier;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = DEFINED_PORT, properties = "spring.h2.console.enabled=true")
public abstract class IntegrationTest {

    @Autowired
    protected DomParser parser;
    @Autowired
    protected LibraryDatabaseService libraryDatabaseService;

    @Autowired
    protected TrackRepository trackRepository;
    @Autowired
    protected AlbumRepository albumRepository;
    @Autowired
    protected ArtistRepository artistRepository;
    @Autowired
    protected TransactionTemplate txTemplate;
    @Autowired
    protected PlaylistRepository playlistRepository;

    @AfterEach
    void reset() {
        trackRepository.deleteAll();
        albumRepository.deleteAll();
        artistRepository.deleteAll();
        playlistRepository.deleteAll();
        parser.reset();
    }

    public <T> T doInTransaction(Supplier<T> method) {
        return txTemplate.execute(tx -> method.get());
    }

    public void doInTransactionVoid(Runnable method) {
        txTemplate.execute(tx -> {
            method.run();
            return tx;
        });
    }
}