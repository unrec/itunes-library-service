package com.unrec.ituneslibrary.config.mapping;

import com.unrec.ituneslibrary.dto.AlbumResponseDto;
import com.unrec.ituneslibrary.model.Album;
import ma.glasnost.orika.MapperFactory;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlbumMappingConfig implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(MapperFactory factory) {
        registerAlbumToAlbumResponseDtoMapper(factory);
    }

    private void registerAlbumToAlbumResponseDtoMapper(MapperFactory factory) {
        factory
                .classMap(Album.class, AlbumResponseDto.class)
                .field("id.name", "name")
                .field("id.year", "year")
                .field("artist.name", "artist")
                .byDefault()
                .register();
    }
}