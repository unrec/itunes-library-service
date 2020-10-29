package com.unrec.ituneslibrary.config.mapping;

import com.unrec.ituneslibrary.dto.TrackResponseDto;
import com.unrec.ituneslibrary.model.Track;
import ma.glasnost.orika.MapperFactory;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrackMappingConfig implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(MapperFactory factory) {
        registerTrackToTrackResponseDtoMapper(factory);
    }

    private void registerTrackToTrackResponseDtoMapper(MapperFactory factory) {
        factory
                .classMap(Track.class, TrackResponseDto.class)
                .field("artist.name", "artist")
                .field("album.id.name", "album")
                .byDefault()
                .register();
    }
}