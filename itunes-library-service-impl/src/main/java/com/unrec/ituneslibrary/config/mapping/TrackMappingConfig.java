package com.unrec.ituneslibrary.config.mapping;

import com.unrec.ituneslibrary.model.Track;
import com.unrec.ituneslibrary.parser.dom.TrackRecord;
import ma.glasnost.orika.MapperFactory;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrackMappingConfig implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(MapperFactory factory) {
        registerTrackRecordToTrackMapper(factory);
    }

    private void registerTrackRecordToTrackMapper(MapperFactory factory) {
        factory
                .classMap(TrackRecord.class, Track.class)
                .field("persistentID", "id")
                .exclude("album")
                .exclude("artist")
                .byDefault()
                .register();
    }
}