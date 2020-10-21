package com.unrec.ituneslibrary.utils;

import com.unrec.ituneslibrary.model.Album;
import com.unrec.ituneslibrary.model.AlbumId;
import com.unrec.ituneslibrary.model.Artist;
import com.unrec.ituneslibrary.model.Playlist;
import com.unrec.ituneslibrary.model.Track;
import com.unrec.ituneslibrary.parser.dom.Library;
import com.unrec.ituneslibrary.parser.dom.TrackRecord;
import com.unrec.ituneslibrary.service.LibraryDatabaseService.AlbumWithArtist;
import lombok.experimental.UtilityClass;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@UtilityClass
public class TestObjects {

    public static final String FULL_LIBRARY_PATH = "src/test/resources/library/full-library.xml";
    public static final String SAMPLE_LIBRARY_PATH = "src/test/resources/library/sample-library.xml";
    public static final String SAMPLE_LIBRARY_NON_ALBUM_PATH = "src/test/resources/library/sample-library-non-album.xml";
    public static final String SAMPLE_LIBRARY_MULTI_DISC_PATH = "src/test/resources/library/sample-library-multi-disc.xml";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    /* MAPPING OBJECTS */

    public static TrackRecord trackRecord() throws MalformedURLException {
        return new TrackRecord()
                .setAlbum("Secret Treaties")
                .setAlbumRating(80)
                .setAlbumRatingComputed(TRUE)
                .setArtist("Blue Öyster Cult")
                .setArtworkCount(1)
                .setBitRate(320)
                .setComments("andrei_47 music collection")
                .setDateAdded(LocalDateTime.parse("2019-09-02T19:25:50Z", DATE_TIME_FORMATTER))
                .setDateModified(LocalDateTime.parse("2019-09-02T17:25:25Z", DATE_TIME_FORMATTER))
                .setFileFolderCount(-1)
                .setGenre("Rock")
                .setKind("Аудиофайл MPEG")
                .setLibraryFolderCount(-1)
                .setLocation(new URL("file://localhost/D:/music/Blue%20%C3%96yster%20Cult/(1974)%20Secret%20Treaties/08%20-%20Astronomy.mp3"))
                .setName("Astronomy")
                .setPersistentID("A17C36C05DA5AA95")
                .setPlayCount(17)
                .setPlayDate(3663238922L)
                .setPlayDateUTC(LocalDateTime.parse("2020-01-30T11:22:02Z", DATE_TIME_FORMATTER))
                .setRating(100)
                .setSampleRate(44100)
                .setSize(15928604L)
                .setSkipCount(1)
                .setSkipDate(LocalDateTime.parse("2019-09-24T08:38:37Z", DATE_TIME_FORMATTER))
                .setTotalTime(388519L)
                .setTrackCount(8)
                .setTrackID(50421)
                .setTrackNumber(8)
                .setTrackType("File")
                .setYear(1974);
    }

    public static Track track(TrackRecord src) {
        return new Track()
                .setId(src.getPersistentID())
                .setName(src.getName())
                .setGenre(src.getGenre())
                .setComposer(src.getComposer())
                .setTrackNumber(src.getTrackNumber())
                .setDiscNumber(src.getDiscNumber())
                .setExplicit(src.getExplicit())
                .setReleaseDate(src.getReleaseDate())
                .setLocation(src.getLocation())
                .setSize(src.getSize())
                .setKind(src.getKind())
                .setBitRate(src.getBitRate())
                .setSampleRate(src.getSampleRate())
                .setBitRate(src.getBitRate())
                .setComments(src.getComments())
                .setPlaylistId(src.getTrackID())
                .setPlayCount(src.getPlayCount())
                .setSkipCount(src.getRating())
                .setRating(src.getRating())
                .setDateAdded(src.getDateAdded())
                .setDateModified(src.getDateModified())
                .setPlayDateUTC(src.getPlayDateUTC())
                .setSkipDate(src.getSkipDate())
                .setPurchased(src.getPurchased())
                .setTrackType(src.getTrackType());
    }

    public static Album album(TrackRecord src) {
        return new Album()
                .setId(albumId(src))
                .setCompilation(src.getCompilation());
    }

    public static AlbumId albumId(TrackRecord src) {
        return new AlbumId()
                .setName(src.getAlbum())
                .setYear(src.getYear());
    }

    public static Artist artist(TrackRecord src) {
        return new Artist().setName(src.getArtist());
    }

    public static AlbumWithArtist albumWithArtist() {
        return new AlbumWithArtist()
                .setAlbum("The Platinum Collection")
                .setYear(2007)
                .setTrackDiscInfo(Map.of(1, 20, 2, 20, 3, 20))
                .setArtist("Ennio Morricone");
    }

    public static AlbumWithArtist albumWithArtist(TrackRecord src) {
        return new AlbumWithArtist()
                .setAlbum(src.getAlbum())
                .setYear(src.getYear())
                .setAlbumArtist(src.getAlbumArtist())
                .setCompilation(src.getCompilation())
                .setArtist(src.getArtist());
    }

    public static Album album(AlbumWithArtist src) {
        return new Album()
                .setId(albumId(src))
                .setArtist(artist(src))
                .setCompilation(src.getCompilation())
                .setTrackDiscInfo(src.getTrackDiscInfo());
    }

    public static AlbumId albumId(AlbumWithArtist src) {
        return new AlbumId()
                .setName(src.getAlbum())
                .setYear(src.getYear());
    }

    public static Artist artist(AlbumWithArtist src) {
        return new Artist().setName(src.getArtist());
    }

    /* REPOSITORY OBJECTS */

    public static Library getTestLibrary() throws MalformedURLException {
        return new Library()
                .setMajorVersion(1)
                .setMinorVersion(1)
                .setDate(LocalDateTime.parse("2020-09-29T15:14:05Z", DATE_TIME_FORMATTER))
                .setApplicationVersion("12.10.9.3")
                .setFeatures(5)
                .setShowContentRatings(true)
                .setMusicFolder(new URL("file://localhost/D:/music/!itunes/iTunes%20Media/iTunes%20Media/"))
                .setLibraryId("DFB4D882E87C2F12");
    }

    public static Track getTestTrack() {
        return new Track()
                .setId("CB226CC410A85072")
                .setName("The Memory Remains")
                .setGenre("Rock")
                .setComposer("Ulrich, Hetfield")
                .setTrackNumber(2)
                .setDiscNumber(1)
                .setExplicit(FALSE)
                .setReleaseDate(LocalDateTime.of(1997, 11, 7, 12, 0));
    }

    public static List<Track> getTestTracks() {
        return Stream.of(
                List.of("Fuel", 1, 1, "3105EFEA81D2FA2C"),
                List.of("The Memory Remains", 2, 1, "CB226CC410A85072"),
                List.of("Devil's Dance", 3, 1, "508523580D5A51DB"),
                List.of("The Unforgiven II", 4, 1, "272E725E54380493"),
                List.of("Better than You", 5, 1, "DD3391F0C2640708"),
                List.of("Slither", 6, 1, "88AECD45DE6C0364"),
                List.of("Carpe Diem Baby", 7, 1, "DCE004861760FEFA"),
                List.of("Bad Seed", 8, 1, "148D5F28A6952DEC"),
                List.of("Where the Wild Things Are", 9, 1, "E87753388E8537E4"),
                List.of("Prince Charming", 10, 1, "504EC2248ED1AB5B"),
                List.of("Low Man's Lyric", 11, 1, "32241132896DD286"),
                List.of("Attitude", 12, 1, "E472613366E87969"),
                List.of("Fixxxer", 13, 1, "CCAFC969C7F125D9"))
                .map(track -> new Track()
                        .setName((String) track.get(0))
                        .setTrackNumber((Integer) track.get(1))
                        .setDiscNumber((Integer) track.get(2))
                        .setId((String) track.get(3)))
                .collect(Collectors.toList());
    }

    public static List<Track> getTestTracks(Album album) {
        List<Track> tracks = getTestTracks();
        tracks.forEach(track -> track.setAlbum(album));
        return tracks;
    }

    public static Album getTestAlbum() {
        return new Album()
                .setId(new AlbumId()
                        .setName("Reload")
                        .setYear(1997))
                        .setArtist(new Artist("Metallica"))
                .setCompilation(FALSE)
                .setTrackDiscInfo(Map.of(1, 13));
    }

    public static Album getTestAlbum(List<Track> tracks) {
        return getTestAlbum().setTracks(tracks);
    }

    public static Artist getTestArtist() {
        return new Artist("Metallica");
    }

    public static Artist getTestArtist(Album album) {
        return getTestArtist().setAlbums(List.of(album));
    }

    public static List<Track> getTestPlaylistTracks() {
        return Stream.of(
                List.of("Rain", "5E63CB16AFAA026E"),
                List.of("Riders on the Storm", "02533058343C7D9D"),
                List.of("It's Probably Me (Soundtrack Version)", "866463152C69B7C1"),
                List.of("Wake Me Up When September Ends", "8835A6918BA301CA"),
                List.of("Inner City Blues (Make Me Wanna Holler)", "56C00A16DE9CF495"))
                .map(track -> new Track()
                        .setName(track.get(0))
                        .setId(track.get(1)))
                .collect(Collectors.toList());
    }

    public static Playlist getTestPlaylist(List<Track> tracks) {
        return new Playlist()
                .setId("93F71C1F12D7B2D9")
                .setName("autumn")
                .setTracks(tracks)
                .setHasAllItems(true)
                .setIsSmart(false)
                .setIsMaster(false);
    }
}