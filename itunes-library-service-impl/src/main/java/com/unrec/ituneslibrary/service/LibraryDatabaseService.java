package com.unrec.ituneslibrary.service;

import com.unrec.ituneslibrary.model.Album;
import com.unrec.ituneslibrary.model.Artist;
import com.unrec.ituneslibrary.model.Track;
import com.unrec.ituneslibrary.parser.dom.DomParser;
import com.unrec.ituneslibrary.parser.dom.TrackRecord;
import com.unrec.ituneslibrary.repository.AlbumRepository;
import com.unrec.ituneslibrary.repository.ArtistRepository;
import com.unrec.ituneslibrary.repository.PlaylistRepository;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
public class LibraryDatabaseService {

    private final DomParser parser;
    private final MapperFacade mapperFacade;

    private final TrackRepository trackRepository;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final PlaylistRepository playlistRepository;

    private List<Track> tracks;
    private Map<AlbumWithArtist, AlbumWithArtist> albumWithArtistMap;
    private List<Artist> artists;
//    private List<Playlist> playlists;

    public void preloadData() throws DocumentException {
        parser.parse();

        tracks = new ArrayList<>();
        artists = new ArrayList<>();
        albumWithArtistMap = new HashMap<>();
//        playlists = new ArrayList<>();

        ArrayList<TrackRecord> trackRecords = new ArrayList<>(parser.getTracks().values());
        for (TrackRecord trackRecord : trackRecords) {
            var track = mapperFacade.map(trackRecord, Track.class);
            tracks.add(track);

            /* fill data to albumWithArtist objects */

            // filter records without required data
            if (trackRecord.getAlbum() == null || trackRecord.getArtist() == null
                    || TRUE.equals(trackRecord.getPodcast()) || TRUE.equals(trackRecord.getMovie())) {
                continue;
            }

            // map track and disc info to an albumWithArtist object
            var albumWithArtist = mapperFacade.map(trackRecord, AlbumWithArtist.class);
            int discNumber = trackRecord.getDiscNumber() != null ? trackRecord.getDiscNumber() : 1;
            int trackCount = trackRecord.getTrackCount() != null ? trackRecord.getTrackCount() : 1;
            if (!albumWithArtistMap.containsKey(albumWithArtist)) {
                albumWithArtist.addDiscInfo(discNumber, trackCount);
                albumWithArtistMap.put(albumWithArtist, albumWithArtist);
            } else {
                albumWithArtistMap.compute(albumWithArtist, (k, v) -> v.addDiscInfo(discNumber, trackCount));
            }
            Artist artist = mapperFacade.map(trackRecord, Artist.class);
            artists.add(artist);
        }
        // TODO: add playlist handling
    }

    public void saveData() {
        saveArtists();
        saveAlbums();
        saveTracks();
    }

    @Transactional
    private void saveTracks() {
        List<Track> mappedTracks = tracks.stream()
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
        List<Artist> mappedArtists = albumWithArtistMap.keySet().stream()
                .map(record -> mapperFacade.map(record, Artist.class))
                .collect(Collectors.toList());

        artistRepository.saveAll((mappedArtists));
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