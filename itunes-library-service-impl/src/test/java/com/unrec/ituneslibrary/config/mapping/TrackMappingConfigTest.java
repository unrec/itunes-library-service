package com.unrec.ituneslibrary.config.mapping;

import com.unrec.ituneslibrary.MappingTest;
import com.unrec.ituneslibrary.dto.TrackResponseDto;
import org.junit.jupiter.api.Test;
import static com.unrec.ituneslibrary.utils.TestObjects.trackResponseDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TrackMappingConfigTest extends MappingTest {

    @Test
    void testMapTrackToTrackResponseDto() {
        var expected = trackResponseDto(track);
        var actual = mapperFacade.map(track, TrackResponseDto.class);

        assertEquals(expected, actual);
    }
}