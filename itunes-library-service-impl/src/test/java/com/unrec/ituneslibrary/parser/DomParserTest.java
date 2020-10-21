package com.unrec.ituneslibrary.parser;

import com.unrec.ituneslibrary.parser.dom.DomParser;
import com.unrec.ituneslibrary.parser.dom.Library;
import com.unrec.ituneslibrary.parser.dom.PlaylistRecord;
import com.unrec.ituneslibrary.parser.dom.TrackRecord;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static com.unrec.ituneslibrary.utils.TestObjects.SAMPLE_LIBRARY_PATH;
import static com.unrec.ituneslibrary.utils.TestObjects.getTestLibrary;
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
        Map<Integer, TrackRecord> tracks = parser.getTracks();
        assertEquals(15, tracks.size());
        assertTrue(tracks.keySet().containsAll(Arrays.asList(
                9459, 9461, 9463, 9465, 9467, 9469, 9471, 9473, 9475, 9477, 9479, 9481, 9483, 9485, 9487)));
    }

    @Test
    void test_ParsePlaylists() {
        Map<Integer, PlaylistRecord> playlists = parser.getPlaylists();
        List<String> playlistNames = playlists.values().stream()
                .map(PlaylistRecord::getName)
                .collect(Collectors.toList());

        String playlist = "beastie boys 5★";
        assertTrue(playlistNames.contains(playlist));

        List<PlaylistRecord> list = playlists.values().stream()
                .filter(pl -> pl.getName().equals(playlist))
                .collect(Collectors.toList());
        assertEquals(37, list.get(0).getPlaylistItems().size());
    }
}