package com.unrec.ituneslibrary.config.mapping;

import com.unrec.ituneslibrary.MappingTest;
import com.unrec.ituneslibrary.model.Album;
import com.unrec.ituneslibrary.model.Artist;
import com.unrec.ituneslibrary.model.Track;
import com.unrec.ituneslibrary.service.LibraryDatabaseService.AlbumWithArtist;
import org.junit.jupiter.api.Test;
import static com.unrec.ituneslibrary.utils.TestObjects.album;
import static com.unrec.ituneslibrary.utils.TestObjects.albumWithArtist;
import static com.unrec.ituneslibrary.utils.TestObjects.artist;
import static com.unrec.ituneslibrary.utils.TestObjects.track;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TrackRecordMappingConfigTest extends MappingTest {

    @Test
    void testMapTrackRecordToTrackEntity() {
        var expected = track(trackRecord);
        var actual = mapperFacade.map(trackRecord, Track.class);

        assertEquals(expected, actual);
    }

    @Test
    void testMapTrackRecordToAlbumEntity() {
        var expected = album(trackRecord);
        var actual = mapperFacade.map(trackRecord, Album.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void testMapTrackRecordToArtistEntity() {
        var expected = artist(trackRecord);
        var actual = mapperFacade.map(trackRecord, Artist.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void testMapTrackRecordToAlbumWithArtistMapper() {
        var expected = albumWithArtist(trackRecord);
        var actual = mapperFacade.map(trackRecord, AlbumWithArtist.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}