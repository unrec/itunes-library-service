package com.unrec.ituneslibrary.service;

import com.unrec.ituneslibrary.IntegrationTest;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.Test;
import java.io.File;
import static com.unrec.ituneslibrary.utils.TestObjects.FULL_LIBRARY_PATH;
import static com.unrec.ituneslibrary.utils.TestObjects.SAMPLE_LIBRARY_MULTI_DISC_PATH;
import static com.unrec.ituneslibrary.utils.TestObjects.SAMPLE_LIBRARY_NON_ALBUM_PATH;
import static com.unrec.ituneslibrary.utils.TestObjects.SAMPLE_LIBRARY_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LibraryDatabaseServiceTest extends IntegrationTest {

    @Test
    void loadFullDatabase() throws DocumentException {
        initDataBaseService(FULL_LIBRARY_PATH);

        assertEquals(25091, trackRepository.count());
        assertEquals(2727, albumRepository.count());
        assertEquals(1515, artistRepository.count());
    }

    @Test
    void loadSampleDatabase() throws DocumentException {
        initDataBaseService(SAMPLE_LIBRARY_PATH);

        assertEquals(12, trackRepository.count());
        assertEquals(1, albumRepository.count());
        assertEquals(1, artistRepository.count());
    }

    @Test
    void loadSampleDatabase_saveNonAlbumRecords() throws DocumentException {
        initDataBaseService(SAMPLE_LIBRARY_NON_ALBUM_PATH);

        assertEquals(5, trackRepository.count());
        assertEquals(0, albumRepository.count());
        assertEquals(0, artistRepository.count());
    }

    @Test
    void loadSampleDatabase_saveMultiDiscAlbum() throws DocumentException {
        initDataBaseService(SAMPLE_LIBRARY_MULTI_DISC_PATH);

        assertEquals(60, trackRepository.count());
        assertEquals(1, albumRepository.count());
        assertEquals(1, artistRepository.count());

        var album = albumRepository.findAll().get(0);
        assertEquals("The Platinum Collection", album.getName());
        assertEquals(2007, album.getYear());
        assertEquals(3, album.getTrackDiscInfo().size());

        var artist = artistRepository.findAll().get(0);
        assertEquals("Ennio Morricone", artist.getName());
    }

    private void initDataBaseService(String path) throws DocumentException {
        parser.setFile(new File(path));
        libraryDatabaseService.preloadData();
        libraryDatabaseService.saveData();
    }
}