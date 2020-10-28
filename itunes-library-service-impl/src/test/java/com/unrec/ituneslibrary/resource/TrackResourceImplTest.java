package com.unrec.ituneslibrary.resource;

import com.unrec.ituneslibrary.IntegrationTest;
import com.unrec.ituneslibrary.dto.PageDto;
import com.unrec.ituneslibrary.dto.TrackResponseDto;
import com.unrec.ituneslibrary.model.Track;
import com.unrec.ituneslibrary.parser.dom.TrackRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.net.MalformedURLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import static com.unrec.ituneslibrary.service.TrackService.TOP_RATING;
import static com.unrec.ituneslibrary.utils.TestObjects.ID_NOT_FOUND;
import static com.unrec.ituneslibrary.utils.TestObjects.album;
import static com.unrec.ituneslibrary.utils.TestObjects.artist;
import static com.unrec.ituneslibrary.utils.TestObjects.fullTrack;
import static com.unrec.ituneslibrary.utils.TestObjects.getTestTracks;
import static com.unrec.ituneslibrary.utils.TestObjects.getTestTracksWithRandomPlayCount;
import static com.unrec.ituneslibrary.utils.TestObjects.track;
import static com.unrec.ituneslibrary.utils.TestObjects.trackRecord;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrackResourceImplTest extends IntegrationTest {

    private static final String BASE_URL = "/api/v1/library/tracks";

    @Test
    @DisplayName("Get the track by id")
    void getOne() throws MalformedURLException {
        var track = trackRepository.save(track(trackRecord()));
        var expected = mapperFacade.map(track, TrackResponseDto.class);

        var response = restTemplate.exchange(
                BASE_URL + "/{id}",
                HttpMethod.GET,
                null,
                TrackResponseDto.class,
                track.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    @DisplayName("Get the track by it's artist, name and album")
    void getOneByAttributes() throws MalformedURLException {
        TrackRecord trackRecord = trackRecord();
        albumRepository.save(album(trackRecord));
        artistRepository.save(artist(trackRecord));

        var track = trackRepository.save(fullTrack(trackRecord));
        var expected = mapperFacade.map(track, TrackResponseDto.class);

        var response = restTemplate.exchange(
                BASE_URL + "/find?artist={artist}&name={name}&album={album}",
                HttpMethod.GET,
                null,
                TrackResponseDto.class,
                track.getArtist().getName(),
                track.getName(),
                track.getAlbum().getId().getName());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    @DisplayName("Return 404 when track not found by id")
    void shouldReturnNotFound() {
        var response = restTemplate.exchange(
                BASE_URL + "/{id}",
                HttpMethod.GET,
                null,
                String.class,
                ID_NOT_FOUND
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Get page of tracks")
    void getPage() {
        var tracks = trackRepository.saveAll(getTestTracks());

        ResponseEntity<PageDto<Track>> response = restTemplate.exchange(
                BASE_URL + "?size={size}&page={page}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDto<Track>>() {
                },
                100, 0
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());

        PageDto<Track> result = response.getBody();
        assertEquals(tracks.size(), result.getTotalCount());
        assertAll(() ->
                result.getData().forEach(track -> assertTrue(trackRepository.existsById(track.getId())))
        );
    }

    @Test
    @DisplayName("Get page of tracks")
    void getPageByRating() {
        int rating = 5;
        var tracks = trackRepository.saveAll(getTestTracks());
        var ratedTracks = tracks.stream()
                .filter(track -> rating == track.getRating())
                .collect(Collectors.toList());

        ResponseEntity<PageDto<Track>> response = restTemplate.exchange(
                BASE_URL + "/rating/{id}?size={size}&page={page}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDto<Track>>() {
                },
                rating, 100, 0
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());

        PageDto<Track> result = response.getBody();
        assertEquals(ratedTracks.size(), result.getTotalCount());
    }

    @Test
    @DisplayName("Get top rated tracks")
    void getTopRated() {
        var tracks = trackRepository.saveAll(getTestTracks());
        var topRatedTracks = tracks.stream()
                .filter(track -> TOP_RATING.equals(track.getRating()))
                .collect(Collectors.toList());

        var response = restTemplate.exchange(
                BASE_URL + "/top/rated/{amount}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Track>>() {
                },
                100
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var result = response.getBody();
        assertEquals(topRatedTracks.size(), result.size());
    }

    @Test
    @DisplayName("Get top played tracks")
    void getTopPlayed() {
        int amount = 2;
        var tracks = trackRepository.saveAll(getTestTracksWithRandomPlayCount());
        var expected = tracks.stream()
                .sorted(Comparator.comparingInt(Track::getPlayCount).reversed())
                .limit(amount)
                .collect(Collectors.toList());

        var response = restTemplate.exchange(
                BASE_URL + "/top/played/{amount}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Track>>() {
                },
                amount
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var actual = response.getBody();
        assertEquals(expected, actual);
    }
}