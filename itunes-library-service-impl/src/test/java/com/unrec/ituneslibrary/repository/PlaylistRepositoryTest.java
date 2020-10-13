package com.unrec.ituneslibrary.repository;

import com.unrec.ituneslibrary.IntegrationTest;
import org.junit.jupiter.api.Test;
import static com.unrec.ituneslibrary.utils.TestObjects.getTestPlaylist;
import static com.unrec.ituneslibrary.utils.TestObjects.getTestPlaylistTracks;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlaylistRepositoryTest extends IntegrationTest {

    @Test
    void addPlaylistWithTracks() {
        var expected = getTestPlaylist(getTestPlaylistTracks());
        var actual = playlistRepository.save(expected);

        assertEquals(expected, actual);
        assertEquals(getTestPlaylistTracks().size(), trackRepository.count());
        assertEquals(1L, playlistRepository.count());
    }
}