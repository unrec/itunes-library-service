package com.unrec.ituneslibrary.repository;

import com.unrec.ituneslibrary.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.TransactionSystemException;
import static com.unrec.ituneslibrary.utils.TestObjects.getTestTrack;
import static com.unrec.ituneslibrary.utils.TestObjects.getTestTracks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TrackRepositoryTest extends IntegrationTest {

    @Test
    void addOneTrack() {
        var expected = getTestTrack();
        trackRepository.save(expected);
        var actual = trackRepository.findById(getTestTrack().getId()).get();

        assertEquals(expected, actual);
        assertEquals(1L, trackRepository.count());
    }

    @Test
    void addOneTrack_withWrongRating() {
        var track = getTestTrack().setRating(6);
        assertThrows(TransactionSystemException.class, () -> trackRepository.save(track));
    }

    @Test
    void addTrackList() {
        var expected = getTestTracks();
        trackRepository.saveAll(expected);
        var actual = trackRepository.findAll();

        assertEquals(expected, actual);
        assertEquals(13L, trackRepository.count());
    }
}