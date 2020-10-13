package com.unrec.ituneslibrary.repository;

import com.unrec.ituneslibrary.IntegrationTest;
import org.junit.jupiter.api.Test;
import static com.unrec.ituneslibrary.utils.TestObjects.*;
import static org.junit.jupiter.api.Assertions.*;

class ArtistRepositoryTest extends IntegrationTest {

    @Test
    void addArtist() {
        var expected = getTestArtist();
        var actual = artistRepository.save(expected);

        assertEquals(expected, actual);
        assertEquals(1L, artistRepository.count());
    }

    @Test
    void addArtistWithAlbumAndTracks() {
        var album = getTestAlbum(getTestTracks());
        var expected = getTestArtist(album);
        var actual = artistRepository.save(expected);

        assertEquals(1L, artistRepository.count());
        assertEquals(1L, albumRepository.count());
        assertEquals(13L, trackRepository.count());
        assertEquals(expected, actual);
    }
}