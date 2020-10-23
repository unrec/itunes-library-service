package com.unrec.ituneslibrary.config.mapping;

import com.unrec.ituneslibrary.model.Album;
import com.unrec.ituneslibrary.model.Artist;
import com.unrec.ituneslibrary.model.Track;
import com.unrec.ituneslibrary.parser.dom.TrackRecord;
import com.unrec.ituneslibrary.service.LibraryDatabaseService;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrackRecordMappingConfig implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(MapperFactory factory) {
        registerTrackRecordToTrackMapper(factory);
        registerTrackRecordToAlbumMapper(factory);
        registerTrackRecordToArtistMapper(factory);
        registerTrackRecordToAlbumWithArtistMapper(factory);
    }

    private void registerTrackRecordToTrackMapper(MapperFactory factory) {
        factory
                .classMap(TrackRecord.class, Track.class)
                .customize(new CustomMapper<>() {
                    @Override
                    public void mapAtoB(TrackRecord trackRecord, Track track, MappingContext context) {
                        if (trackRecord.getRating() == null) {
                            track.setRating(0);
                        } else
                            track.setRating(track.getRating() / 20);
                    }
                })
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
}