package com.unrec.ituneslibrary.config.mapping;

import com.unrec.ituneslibrary.MappingTest;
import com.unrec.ituneslibrary.dto.AlbumResponseDto;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static com.unrec.ituneslibrary.utils.TestObjects.albumResponseDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AlbumMappingConfigTest extends MappingTest {

    @Test
    void testMapAlbumToAlbumResponseDto() {
        album.setArtist(artist)
                .setTracks(List.of(track))
                .setTrackDiscInfo(Map.of(trackRecord.getDiscNumber(), trackRecord.getTrackCount()));
        var expected = albumResponseDto(album);
        var actual = mapperFacade.map(album, AlbumResponseDto.class);

        assertEquals(expected, actual);
    }
}