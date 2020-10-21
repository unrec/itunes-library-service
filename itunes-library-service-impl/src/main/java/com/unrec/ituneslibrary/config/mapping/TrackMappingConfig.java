package com.unrec.ituneslibrary.config.mapping;

import com.unrec.ituneslibrary.model.Album;
import com.unrec.ituneslibrary.model.Artist;
import com.unrec.ituneslibrary.model.Track;
import com.unrec.ituneslibrary.parser.dom.TrackRecord;
import com.unrec.ituneslibrary.service.LibraryDatabaseService;
import ma.glasnost.orika.MapperFactory;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrackMappingConfig implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(MapperFactory factory) {
        registerTrackRecordToTrackMapper(factory);
        registerTrackRecordToAlbumMapper(factory);
        registerTrackRecordToArtistMapper(factory);
        registerTrackRecordToAlbumWithArtistMapper(factory);
        registerAlbumWithArtistToAlbumMapper(factory);
        registerAlbumWithArtistToArtistMapper(factory);
    }

    private void registerTrackRecordToTrackMapper(MapperFactory factory) {
        factory
                .classMap(TrackRecord.class, Track.class)
                .field("persistentID", "id")
                .field("trackID", "playlistId")
                .field("album", "album.id.name")
                .field("year", "album.id.year")
                .byDefault()
                .register();
    }

    private void registerTrackRecordToAlbumMapper(MapperFactory factory) {
        factory
                .classMap(TrackRecord.class, Album.class)
                .field("album", "id.name")
                .field("year", "id.year")
                .exclude("artist")
                .byDefault()
                .register();
    }

    private void registerTrackRecordToArtistMapper(MapperFactory factory) {
        factory
                .classMap(TrackRecord.class, Artist.class)
                .field("artist", "name")
                .byDefault()
                .register();
    }

    private void registerTrackRecordToAlbumWithArtistMapper(MapperFactory factory) {
        factory
                .classMap(TrackRecord.class, LibraryDatabaseService.AlbumWithArtist.class)
                .byDefault()
                .register();
    }

    private void registerAlbumWithArtistToAlbumMapper(MapperFactory factory) {
        factory
                .classMap(LibraryDatabaseService.AlbumWithArtist.class, Album.class)
                .field("album", "id.name")
                .field("year", "id.year")
                .field("artist","artist.name")
//                .exclude("artist")
                .byDefault()
                .register();
    }

    private void registerAlbumWithArtistToArtistMapper(MapperFactory factory) {
        factory
                .classMap(LibraryDatabaseService.AlbumWithArtist.class, Artist.class)
                .field("artist", "name")
                .byDefault()
                .register();
    }
}