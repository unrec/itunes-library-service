package com.unrec.ituneslibrary.config.mapping;

import com.ituneslibrary.dto.TrackResponseDto;
import com.unrec.ituneslibrary.model.Album;
import com.unrec.ituneslibrary.model.Artist;
import com.unrec.ituneslibrary.model.Track;
import com.unrec.ituneslibrary.parser.dom.TrackRecord;
import com.unrec.ituneslibrary.utils.MapperUtils;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.net.MalformedURLException;
import static com.unrec.ituneslibrary.utils.TestObjects.album;
import static com.unrec.ituneslibrary.utils.TestObjects.artist;
import static com.unrec.ituneslibrary.utils.TestObjects.track;
import static com.unrec.ituneslibrary.utils.TestObjects.trackRecord;
import static com.unrec.ituneslibrary.utils.TestObjects.trackResponseDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TrackMappingConfigTest {

    private static TrackRecord trackRecord;
    private static Track track;
    private static Album album;
    private static Artist artist;
    private final MapperFacade mapperFacade = MapperUtils.createMapper();

    @BeforeAll
    static void setUp() throws MalformedURLException {
        trackRecord = trackRecord();
        album = album(trackRecord);
        artist = artist(trackRecord);
        track = track(trackRecord)
                .setAlbum(album)
                .setArtist(artist);
    }

    @Test
    void testMapTrackToTrackResponseDto() {
        var expected = trackResponseDto(track);
        var actual = mapperFacade.map(track, TrackResponseDto.class);

        assertEquals(expected, actual);
    }
}