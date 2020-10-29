package com.unrec.ituneslibrary;

import com.unrec.ituneslibrary.model.Album;
import com.unrec.ituneslibrary.model.Artist;
import com.unrec.ituneslibrary.model.Track;
import com.unrec.ituneslibrary.parser.dom.TrackRecord;
import com.unrec.ituneslibrary.service.LibraryDatabaseService.AlbumWithArtist;
import com.unrec.ituneslibrary.utils.MapperUtils;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.BeforeAll;
import java.net.MalformedURLException;
import static com.unrec.ituneslibrary.utils.TestObjects.album;
import static com.unrec.ituneslibrary.utils.TestObjects.albumWithArtist;
import static com.unrec.ituneslibrary.utils.TestObjects.artist;
import static com.unrec.ituneslibrary.utils.TestObjects.track;
import static com.unrec.ituneslibrary.utils.TestObjects.trackRecord;

public abstract class MappingTest {

    protected static TrackRecord trackRecord;
    protected static Track track;
    protected static Album album;
    protected static Artist artist;
    protected static AlbumWithArtist albumWithArtist;
    protected final MapperFacade mapperFacade = MapperUtils.createMapper();

    @BeforeAll
    static void setUp() throws MalformedURLException {
        trackRecord = trackRecord();
        album = album(trackRecord);
        artist = artist(trackRecord);
        track = track(trackRecord)
                .setAlbum(album)
                .setArtist(artist);

        albumWithArtist = albumWithArtist();
    }
}