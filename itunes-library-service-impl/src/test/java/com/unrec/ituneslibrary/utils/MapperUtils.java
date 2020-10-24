package com.unrec.ituneslibrary.utils;

import com.unrec.ituneslibrary.config.mapping.AlbumWithArtistMappingConfig;
import com.unrec.ituneslibrary.config.mapping.TrackMappingConfig;
import com.unrec.ituneslibrary.config.mapping.TrackRecordMappingConfig;
import lombok.experimental.UtilityClass;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.util.ReflectionUtils.doWithFields;

@UtilityClass
public class MapperUtils {

    private static final String ASSERTION_NULL_MESSAGE = "'%s' is null! Do you forget to add it to mapper test?";

    public static MapperFacade createMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        new TrackRecordMappingConfig().configure(mapperFactory);
        new AlbumWithArtistMappingConfig().configure(mapperFactory);
        new TrackMappingConfig().configure(mapperFactory);
        return mapperFactory.getMapperFacade();
    }

    public static void assertAllFieldNonNull(Object object) {
        doWithFields(
                object.getClass(),
                field -> assertNotNull(getField(object, field.getName()), String.format(ASSERTION_NULL_MESSAGE, field.getName()))
        );
    }
}