package com.unrec.ituneslibrary.repository;

import com.unrec.ituneslibrary.IntegrationTest;
import org.junit.jupiter.api.Test;
import static com.unrec.ituneslibrary.utils.TestObjects.getTestAlbum;
import static com.unrec.ituneslibrary.utils.TestObjects.getTestTracks;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AlbumRepositoryTest extends IntegrationTest {

    @Test
    void addEmptyAlbum() {
        var expected = albumRepository.save(getTestAlbum());
        var actual = albumRepository.findById(expected.getId()).get();
        assertEquals(1L, albumRepository.count());
        assertEquals(expected, actual);
    }

    @Test
    void addAlbumWithTracks() {
        var actual = getTestAlbum(getTestTracks());
        var expected = albumRepository.save(actual);

        assertEquals(getTestTracks().size(), trackRepository.count());
        assertEquals(1L, albumRepository.count());
        assertEquals(expected, actual);
    }
}