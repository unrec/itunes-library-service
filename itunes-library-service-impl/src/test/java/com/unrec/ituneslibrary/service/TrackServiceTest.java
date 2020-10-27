package com.unrec.ituneslibrary.service;

import com.unrec.ituneslibrary.exception.BadRequestException;
import com.unrec.ituneslibrary.exception.NotFoundException;
import com.unrec.ituneslibrary.repository.TrackRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import java.util.Optional;
import java.util.stream.Collectors;
import static com.unrec.ituneslibrary.service.TrackService.TOP_RATING;
import static com.unrec.ituneslibrary.utils.TestObjects.ID_NOT_FOUND;
import static com.unrec.ituneslibrary.utils.TestObjects.SORT_PARAMETER;
import static com.unrec.ituneslibrary.utils.TestObjects.WRONG_SORT_DIRECTION;
import static com.unrec.ituneslibrary.utils.TestObjects.getTestAlbum;
import static com.unrec.ituneslibrary.utils.TestObjects.getTestArtist;
import static com.unrec.ituneslibrary.utils.TestObjects.getTestTrack;
import static com.unrec.ituneslibrary.utils.TestObjects.getTestTracks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrackServiceTest {

    @InjectMocks
    TrackService trackService;
    @Mock
    private TrackRepository trackRepository;

    @AfterEach
    void tearDown() {
        Mockito.reset(trackRepository);
    }

    @Test
    @DisplayName("Get track by id")
    void getById() {
        var expected = getTestTrack();
        when(trackRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
        var actual = trackService.getById(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Throw NotFoundException when getting track with non-existing id")
    void shouldThrow_whenNotFound() {
        assertThrows(NotFoundException.class, () -> trackService.getById(ID_NOT_FOUND));
    }

    @Test
    @DisplayName("Get track by attributes")
    void getByAttributes() {
        var expected = getTestTrack()
                .setAlbum(getTestAlbum())
                .setArtist(getTestArtist());

        String artistName = expected.getArtist().getName();
        String trackName = expected.getName();
        String albumName = expected.getAlbum().getId().getName();

        when(trackRepository.findByAttributes(artistName, trackName, albumName))
                .thenReturn(Optional.of(expected));
        var actual = trackService.getByAttributes(artistName, trackName, albumName);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get top rated tracks")
    void getTopRated() {
        var expected = getTestTracks().stream().filter(track -> TOP_RATING.equals(track.getRating())).collect(Collectors.toList());
        when(trackRepository.findAllByRating(eq(TOP_RATING), ArgumentMatchers.any(Sort.class)))
                .thenReturn(expected);
        var actual = trackService.getTopRated(expected.size(), "DESC", "dateAdded");

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Throw BadRequestException when passing wrong type of sort")
    void shouldThrow_whenWrongSortDirection() {
        assertThrows(BadRequestException.class, () -> trackService.getTopRated(TOP_RATING, WRONG_SORT_DIRECTION, SORT_PARAMETER));
    }
}