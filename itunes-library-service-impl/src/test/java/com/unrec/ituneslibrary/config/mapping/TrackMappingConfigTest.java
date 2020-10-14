package com.unrec.ituneslibrary.config.mapping;

import com.unrec.ituneslibrary.model.Track;
import com.unrec.ituneslibrary.utils.MapperUtils;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.Test;
import java.net.MalformedURLException;
import static com.unrec.ituneslibrary.utils.TestObjects.track;
import static com.unrec.ituneslibrary.utils.TestObjects.trackRecord;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TrackMappingConfigTest {

    private final MapperFacade mapperFacade = MapperUtils.createMapper();

    @Test
    void testMapTrackRecordToTrackEntity() throws MalformedURLException {
        var trackRecord = trackRecord();
        var expected = track(trackRecord);
        var actual = mapperFacade.map(trackRecord, Track.class);

        assertEquals(expected, actual);
    }
}