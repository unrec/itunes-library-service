package com.unrec.ituneslibrary.service;

import com.unrec.ituneslibrary.model.Album;
import com.unrec.ituneslibrary.model.Artist;
import com.unrec.ituneslibrary.model.Track;
import com.unrec.ituneslibrary.parser.dom.DomParser;
import com.unrec.ituneslibrary.parser.dom.TrackRecord;
import com.unrec.ituneslibrary.repository.AlbumRepository;
import com.unrec.ituneslibrary.repository.ArtistRepository;
import com.unrec.ituneslibrary.repository.TrackRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ma.glasnost.orika.MapperFacade;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
public class LibraryDatabaseService {

    public static final String VARIOUS_ARTISTS = "VARIOUS_ARTISTS";

    private final DomParser parser;
    private final MapperFacade mapperFacade;

    private final TrackRepository trackRepository;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    private List<Track> validTracks;
    private Map<AlbumWithArtist, AlbumWithArtist> albumWithArtistMap;
    private HashSet<Artist> artists;

    private List<TrackRecord> audiobooks;
    private List<TrackRecord> podcasts;
    private List<TrackRecord> deficientRecords;

    public void preloadData() throws DocumentException {
        validTracks = new ArrayList<>();
        albumWithArtistMap = new HashMap<>();
        artists = new HashSet<>();

        audiobooks = new ArrayList<>();
        podcasts = new ArrayList<>();
        deficientRecords = new ArrayList<>();

        boolean hasCompilations = false;

        parser.parse();
        ArrayList<TrackRecord> trackRecords = new ArrayList<>(parser.getTracks().values());
        for (TrackRecord record : trackRecords) {

            // Handle non-music records
            if (isMovie(record)) {
                continue;
            }
            if (isAudiobook(record)) {
                audiobooks.add(record);
                continue;
            }
            if (isPodcast(record)) {
                podcasts.add(record);
                continue;
            }
            if (!isValidRecord(record)) {
                deficientRecords.add(record);
                continue;
            }

            /* Fill data to albumWithArtist objects */
            var artist = mapperFacade.map(record, Artist.class);
            artists.add(artist);
            if (hasCompilations) {
                artists.add(new Artist(VARIOUS_ARTISTS));
            }

            var track = mapperFacade.map(record, Track.class);
            validTracks.add(track);

            // Map track and disc info to an albumWithArtist object
            var albumWithArtist = mapperFacade.map(record, AlbumWithArtist.class);
            if (TRUE.equals(albumWithArtist.getCompilation())) {
                albumWithArtist.setArtist(VARIOUS_ARTISTS);
                hasCompilations = true;
            }
            int discNumber = record.getDiscNumber() != null ? record.getDiscNumber() : 1;
            int trackCount = record.getTrackCount() != null ? record.getTrackCount() : 1;
            if (!albumWithArtistMap.containsKey(albumWithArtist)) {
                albumWithArtist.addDiscInfo(discNumber, trackCount);
                albumWithArtistMap.put(albumWithArtist, albumWithArtist);
            } else {
                albumWithArtistMap.compute(albumWithArtist, (k, v) -> v.addDiscInfo(discNumber, trackCount));
            }
        }
        if (hasCompilations) {
            artists.add(new Artist(VARIOUS_ARTISTS));
        }
        // TODO: add playlist handling
    }

    public void saveData() {
        saveArtists();
        saveAlbums();
        saveTracks();
    }

    private boolean isAudiobook(TrackRecord record) {
        return "Audiobooks".equalsIgnoreCase(record.getGenre())
                || "Аудиокниги".equalsIgnoreCase(record.getGenre());
    }

    private boolean isPodcast(TrackRecord record) {
        return record.getPodcast() == null ? FALSE : record.getPodcast();
    }

    private boolean isMovie(TrackRecord record) {
        return record.getMovie() == null ? FALSE : record.getMovie();
    }

    private boolean isValidRecord(TrackRecord trackRecord) {
        return trackRecord.getAlbum() != null
                && trackRecord.getArtist() != null
                && trackRecord.getYear() != null;
    }

    @Transactional
    private void saveTracks() {
        List<Track> mappedTracks = validTracks.stream()
                .map(track -> mapperFacade.map(track, Track.class))
                .collect(Collectors.toList());
        trackRepository.saveAll(mappedTracks);
    }

    @Transactional
    private void saveAlbums() {
        List<Album> mappedAlbums = albumWithArtistMap.keySet().stream()
                .map(record -> mapperFacade.map(record, Album.class))
                .collect(Collectors.toList());
        albumRepository.saveAll(mappedAlbums);
    }

    @Transactional
    private void saveArtists() {
        artistRepository.saveAll(artists);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AlbumWithArtist {
        private String album;
        private Integer year;
        private String albumArtist;
        private Boolean compilation;
        private Map<Integer, Integer> trackDiscInfo = new HashMap<>();

        private String artist;

        private AlbumWithArtist addDiscInfo(Integer key, Integer value) {
            trackDiscInfo.put(key, value);
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            AlbumWithArtist that = (AlbumWithArtist) o;

            if (!album.equals(that.album)) return false;
            return artist.equals(that.artist);
        }

        @Override
        public int hashCode() {
            int result = album.hashCode();
            result = 31 * result + (year != null ? year.hashCode() : 0);
            result = 31 * result + artist.hashCode();
            return result;
        }
    }
}