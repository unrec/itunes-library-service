package com.unrec.ituneslibrary.config.mapping;

import com.unrec.ituneslibrary.model.Album;
import com.unrec.ituneslibrary.model.Artist;
import com.unrec.ituneslibrary.service.LibraryDatabaseService.AlbumWithArtist;
import com.unrec.ituneslibrary.utils.MapperUtils;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static com.unrec.ituneslibrary.utils.TestObjects.album;
import static com.unrec.ituneslibrary.utils.TestObjects.albumWithArtist;
import static com.unrec.ituneslibrary.utils.TestObjects.artist;
import static org.assertj.core.api.Assertions.assertThat;

class AlbumWithArtistMappingConfigTest {

    private static AlbumWithArtist albumWithArtist;
    private final MapperFacade mapperFacade = MapperUtils.createMapper();

    @BeforeAll
    static void setUp() {
        albumWithArtist = albumWithArtist();
    }


    @Test
    void testMapAlbumWithArtistToAlbumMapper() {
        var expected = album(albumWithArtist);
        var actual = mapperFacade.map(albumWithArtist, Album.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void testMapAlbumWithArtistToArtistMapper() {
        var expected = artist(albumWithArtist);
        var actual = mapperFacade.map(albumWithArtist, Artist.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringActualNullFields()
                .isEqualTo(expected);
    }
}