package com.unrec.ituneslibrary.utils;

import com.unrec.ituneslibrary.model.Album;
import com.unrec.ituneslibrary.model.Artist;
import com.unrec.ituneslibrary.model.Playlist;
import com.unrec.ituneslibrary.model.Track;
import com.unrec.ituneslibrary.parser.dom.Library;
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

@UtilityClass
public class TestObjects {

    public static final String SAMPLE_LIBRARY_PATH = "src/test/resources/sample-library.xml";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

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
                .setName("Reload")
                .setYear(1997)
                .setAlbumArtist("Metallica")
                .setCompilation(FALSE)
                .setTrackDiscInfo(Map.of(1, 13));
    }

    public static Album getTestAlbum(List<Track> tracks) {
        return getTestAlbum().setTracks(tracks);
    }

    public static Artist getTestArtist() {
        return new Artist().setName("Metallica");
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