package com.unrec.ituneslibrary.config.mapping;

import com.unrec.ituneslibrary.model.Album;
import com.unrec.ituneslibrary.model.Artist;
import com.unrec.ituneslibrary.service.LibraryDatabaseService;
import ma.glasnost.orika.MapperFactory;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration

public class AlbumWithArtistMappingConfig implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(MapperFactory factory) {
        registerAlbumWithArtistToAlbumMapper(factory);
        registerAlbumWithArtistToArtistMapper(factory);
    }

    private void registerAlbumWithArtistToAlbumMapper(MapperFactory factory) {
        factory
                .classMap(LibraryDatabaseService.AlbumWithArtist.class, Album.class)
                .field("album", "id.name")
                .field("year", "id.year")
                .field("artist","artist.name")
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