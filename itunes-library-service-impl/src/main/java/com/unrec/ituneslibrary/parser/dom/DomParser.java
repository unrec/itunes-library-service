package com.unrec.ituneslibrary.parser.dom;

import com.unrec.ituneslibrary.parser.XmlParser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

/**
 * Updated and refactored parser from https://github.com/pireba/itunes-parser
 */
@Slf4j
@Service
@NoArgsConstructor
public class DomParser implements XmlParser {

    private static final String XPATH_LIBRARY = "/plist/dict";
    private static final String XPATH_TRACKS = "/plist/dict/dict/dict";
    private static final String XPATH_PLAYLISTS = "/plist/dict/array/dict";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @Getter
    private Library library = new Library();
    @Getter
    private Map<Integer, TrackRecord> tracks = new HashMap<>();
    @Getter
    private Map<Integer, PlaylistRecord> playlists = new HashMap<>();

    private File file;

    /* Parser API */
    @Override
    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public void setFile(InputStream inputStream) throws IOException {
        this.file = new File("library.xml");
        FileUtils.copyInputStreamToFile(inputStream, this.file);
    }

    @Override
    public void parse() throws DocumentException {
        SAXReader reader = new SAXReader();
        reader.addHandler(XPATH_LIBRARY, getLibraryHandler());
        reader.addHandler(XPATH_TRACKS, getTrackHandler());
        reader.addHandler(XPATH_PLAYLISTS, getPlaylistHandler());
        reader.read(this.file);
    }

    /* HANDLERS */
    private ElementHandler getLibraryHandler() {
        return new ElementHandler() {
            @Override
            public void onStart(ElementPath elementPath) {
            }

            @Override
            public void onEnd(ElementPath elementPath) {
                addPropertiesToObject(library, elementPath);
            }
        };
    }

    private ElementHandler getTrackHandler() {
        return new ElementHandler() {
            @Override
            public void onStart(ElementPath elementPath) {
            }

            @Override
            public void onEnd(ElementPath elementPath) {
                TrackRecord track = addPropertiesToObject(new TrackRecord(), elementPath);
                tracks.put(track.getTrackID(), track);
            }
        };
    }

    private ElementHandler getPlaylistHandler() {
        return new ElementHandler() {
            @Override
            public void onStart(ElementPath elementPath) {
            }

            @Override
            public void onEnd(ElementPath elementPath) {
                PlaylistRecord playlist = addPropertiesToObject(new PlaylistRecord(), elementPath);
                playlists.put(playlist.getPlaylistID(), playlist);
            }
        };
    }

    /* ADDING METHODS */
    private <T> T addPropertiesToObject(T object, ElementPath elementPath) {
        List<Element> elements = elementPath.getCurrent().elements();

        for (int i = 0; i < elements.size(); i++) {
            String key = elements.get(i).getText();
            if (!"key".equals(elements.get(i).getName())) {
                continue;
            } else if ("Tracks".equals(key)) {
                continue;
            } else if ("Playlists".equals(key)) {
                continue;
            }

            i++;
            String value;
            if ("true".equals(elements.get(i).getName())) {
                value = "true";
            } else if ("false".equals(elements.get(i).getName())) {
                value = "false";
            } else if (object instanceof PlaylistRecord && "array".equals(elements.get(i).getName())) {
                addTracksToPlaylist(elements.get(i), (PlaylistRecord) object);
                continue;
            } else {
                value = elements.get(i).getText();
            }

            if (object instanceof Library) {
                try {
                    addLibraryProperty((Library) object, key, value);
                } catch (Exception e) {
                    log.error("Error while parsing a Library property \"{}\". {}: {}", key, e.getClass().getSimpleName(), e.getMessage());
                }
            } else if (object instanceof TrackRecord) {
                try {
                    addTrackProperty((TrackRecord) object, key, value);
                } catch (Exception e) {
                    log.error("Error while parsing a Track property \"{}\". {}: {}", key, e.getClass().getSimpleName(), e.getMessage());
                }
            } else if (object instanceof PlaylistRecord) {
                try {
                    addPlaylistProperty((PlaylistRecord) object, key, value);
                } catch (Exception e) {
                    log.error("Error while parsing a Playlist property \"{}\". {}: {}", key, e.getClass().getSimpleName(), e.getMessage());
                }
            }
        }
        return object;
    }

    private void addLibraryProperty(Library library, String key, String value) throws Exception {
        switch (key) {
            case "Major Version":
                library.setMajorVersion(parseInt(value));
                break;
            case "Minor Version":
                library.setMinorVersion(parseInt(value));
                break;
            case "Date":
                library.setDate(LocalDateTime.parse(value, DATE_TIME_FORMATTER));
                break;
            case "Application Version":
                library.setApplicationVersion(value);
                break;
            case "Features":
                library.setFeatures(parseInt(value));
                break;
            case "Show Content Ratings":
                library.setShowContentRatings(parseBoolean(value));
                break;
            case "Music Folder":
                library.setMusicFolder(new URL(value));
                break;
            case "Library Persistent ID":
                library.setLibraryId(value);
                break;
            default:
                log.warn("Unknown Library property:\"{}\" with value \"{}\"'", key, value);
                break;
        }
    }

    private void addTrackProperty(TrackRecord track, String key, String value) throws Exception {
        switch (key) {
            case "Album":
                track.setAlbum(value);
                break;
            case "Album Artist":
                track.setAlbumArtist(value);
                break;
            case "Album Rating":
                track.setAlbumRating(parseInt(value));
                break;
            case "Album Rating Computed":
            case "Rating Computed":
                track.setAlbumRatingComputed(parseBoolean(value));
                break;
            case "Artist":
                track.setArtist(value);
                break;
            case "Artwork Count":
                track.setArtworkCount(parseInt(value));
                break;
            case "Bit Rate":
                track.setBitRate(parseInt(value));
                break;
            case "BPM":
                track.setBpm(parseInt(value));
                break;
            case "Comments":
                track.setComments(value);
                break;
            case "Compilation":
                track.setCompilation(parseBoolean(value));
                break;
            case "Composer":
                track.setComposer(value);
                break;
            case "Clean":
                track.setClean(parseBoolean(value));
                break;
            case "Date Added":
                track.setDateAdded(LocalDateTime.parse(value, DATE_TIME_FORMATTER));
                break;
            case "Date Modified":
                track.setDateModified(LocalDateTime.parse(value, DATE_TIME_FORMATTER));
                break;
            case "Disc Count":
                track.setDiscCount(parseInt(value));
                break;
            case "Disc Number":
                track.setDiscNumber(parseInt(value));
                break;
            case "Disabled":
                track.setDisabled(parseBoolean(value));
                break;
            case "Episode":
                track.setEpisode(value);
                break;
            case "Episode Order":
                track.setEpisodeOrder(parseInt(value));
                break;
            case "Equalizer":
                track.setEqualizer(value);
                break;
            case "Explicit":
                track.setExplicit(parseBoolean(value));
                break;
            case "File Folder Count":
                track.setFileFolderCount(parseInt(value));
                break;
            case "File Type":
                track.setFileType(Long.parseLong(value));
                break;
            case "Genre":
                track.setGenre(value);
                break;
            case "Grouping":
                track.setGrouping(value);
                break;
            case "Kind":
                track.setKind(value);
                break;
            case "Library Folder Count":
                track.setLibraryFolderCount(parseInt(value));
                break;
            case "Location":
                track.setLocation(new URL(value));
                break;
            case "Loved":
                track.setLoved(parseBoolean(value));
                break;
            case "Name":
                track.setName(value);
                break;
            case "Normalization":
                track.setNormalization(parseInt(value));
                break;
            case "Part Of Gapless Album":
                track.setPartOfGaplessAlbum(parseBoolean(value));
                break;
            case "Persistent ID":
                track.setPersistentID(value);
                break;
            case "Play Count":
                track.setPlayCount(parseInt(value));
                break;
            case "Play Date":
                track.setPlayDate(Long.parseLong(value));
                break;
            case "Play Date UTC":
                track.setPlayDateUTC(LocalDateTime.parse(value, DATE_TIME_FORMATTER));
                break;
            case "Podcast":
                track.setPodcast(parseBoolean(value));
            case "Purchased":
                track.setPurchased(parseBoolean(value));
                break;
            case "Rating":
                track.setRating(parseInt(value));
                break;
            case "Release Date":
                track.setReleaseDate(LocalDateTime.parse(value, DATE_TIME_FORMATTER));
                break;
            case "Sample Rate":
                track.setSampleRate(parseInt(value));
                break;
            case "Size":
                track.setSize(Long.parseLong(value));
                break;
            case "Skip Count":
                track.setSkipCount(parseInt(value));
                break;
            case "Skip Date":
                track.setSkipDate(LocalDateTime.parse(value, DATE_TIME_FORMATTER));
                break;
            case "Season":
                track.setSeason(parseInt(value));
                break;
            case "Series":
                track.setSeries(value);
                break;
            case "Sort Album":
                track.setSortAlbum(value);
                break;
            case "Sort Album Artist":
                track.setSortAlbumArtist(value);
                break;
            case "Sort Artist":
                track.setSortArtist(value);
                break;
            case "Sort Composer":
                track.setSortComposer(value);
                break;
            case "Sort Name":
                track.setSortName(value);
                break;
            case "Sort Series":
                track.setSortSeries(value);
                break;
            case "Start Time":
                track.setStartTime(Long.parseLong(value));
                break;
            case "Stop Time":
                track.setStopTime(Long.parseLong(value));
                break;
            case "Total Time":
                track.setTotalTime(Long.parseLong(value));
                break;
            case "Track Count":
                track.setTrackCount(parseInt(value));
                break;
            case "Track ID":
                track.setTrackID(parseInt(value));
                break;
            case "Track Number":
                track.setTrackNumber(parseInt(value));
                break;
            case "Track Type":
                track.setTrackType(value);
                break;
            case "Volume Adjustment":
                track.setVolumeAdjustment(parseInt(value));
                break;
            case "Year":
                track.setYear(parseInt(value));
                break;
            default:
                log.warn("Unknown Track property:\"{}\" with value \"{}\"'", key, value);
                break;
        }
    }

    private void addPlaylistProperty(PlaylistRecord playlist, String key, String value) throws Exception {
        switch (key) {
            case "All Items":
                playlist.setAllItems(parseBoolean(value));
                break;
            case "Audiobooks":
                playlist.setAudiobooks(parseBoolean(value));
                break;
            case "Distinguished Kind":
                playlist.setDistinguishedKind(parseInt(value));
                break;
            case "Folder":
                playlist.setFolder(parseBoolean(value));
                break;
            case "Master":
                playlist.setMaster(parseBoolean(value));
                break;
            case "Movies":
                playlist.setMovies(parseBoolean(value));
                break;
            case "Music":
                playlist.setMusic(parseBoolean(value));
                break;
            case "Name":
                playlist.setName(value);
                break;
            case "Parent Persistent ID":
                playlist.setParentPersistentID(value);
                break;
            case "Playlist ID":
                playlist.setPlaylistID(parseInt(value));
                break;
            case "Playlist Items":
                break;
            case "Playlist Persistent ID":
                playlist.setPlaylistPersistentID(value);
                break;
            case "Podcasts":
                playlist.setPodcasts(parseBoolean(value));
                break;
            case "Smart Criteria":
                value = value.replaceAll("\\s+", "");
                playlist.setSmartCriteria(value);
                break;
            case "Smart Info":
                value = value.replaceAll("\\s+", "");
                playlist.setSmartInfo(value);
                break;
            case "TV Shows":
                playlist.setTvShows(parseBoolean(value));
                break;
            case "Visible":
                playlist.setVisible(parseBoolean(value));
                break;
            default:
                log.warn("Unknown Playlist property:\"{}\" with value \"{}\"'", key, value);
                break;
        }
    }

    private void addTracksToPlaylist(Element element, PlaylistRecord playlist) {
        List<Element> elements = element.elements();
        Map<Integer, TrackRecord> tracksToPlaylist = new HashMap<>();

        for (Element value : elements) {
            Element el = value.elements().get(1);
            int id = parseInt(el.getText());
            TrackRecord track = tracks.get(id);
            tracksToPlaylist.put(id, track);
        }
        playlist.setPlaylistItems(tracksToPlaylist);
    }
}