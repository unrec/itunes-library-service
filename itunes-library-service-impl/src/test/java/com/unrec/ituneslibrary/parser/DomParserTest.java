package com.unrec.ituneslibrary.parser;

import com.unrec.ituneslibrary.parser.dom.DomParser;
import com.unrec.ituneslibrary.parser.dom.Library;
import com.unrec.ituneslibrary.parser.dom.Playlist;
import com.unrec.ituneslibrary.parser.dom.Track;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static com.unrec.ituneslibrary.parser.utils.TestObjects.SAMPLE_LIBRARY_PATH;
import static com.unrec.ituneslibrary.parser.utils.TestObjects.getTestLibrary;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DomParserTest {

    private DomParser parser;

    @BeforeEach
    void setUp() throws DocumentException {
        parser = new DomParser();
        File file = new File(SAMPLE_LIBRARY_PATH);
        parser.setFile(file);
        parser.parse();
    }

    @Test
    void test_ParseLibrary() throws MalformedURLException {
        Library expected = getTestLibrary();
        Library actual = parser.getLibrary();
        assertEquals(actual, expected);
    }

    @Test
    void test_ParseTracks() {
        Map<Integer, Track> tracks = parser.getTracks();
        assertTrue(tracks.keySet().containsAll(Arrays.asList(
                3585, 3601, 3587, 3603, 3589, 3591, 3593, 3595, 3581, 3597, 3583, 3599)));
    }

    @Test
    void test_ParsePlaylists() {
        Map<Integer, Playlist> playlists = parser.getPlaylists();
        List<String> playlistNames = playlists.values().stream()
                .map(Playlist::getName)
                .collect(Collectors.toList());

        String playlist = "beastie boys 5★";
        assertTrue(playlistNames.contains(playlist));

        List<Playlist> list = playlists.values().stream()
                .filter(pl -> pl.getName().equals(playlist))
                .collect(Collectors.toList());
        assertEquals(37, list.get(0).getPlaylistItems().size());
    }
}