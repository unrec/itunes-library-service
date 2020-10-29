package com.unrec.ituneslibrary.config.mapping;

import com.unrec.ituneslibrary.MappingTest;
import com.unrec.ituneslibrary.model.Album;
import com.unrec.ituneslibrary.model.Artist;
import org.junit.jupiter.api.Test;
import static com.unrec.ituneslibrary.utils.TestObjects.album;
import static com.unrec.ituneslibrary.utils.TestObjects.artist;
import static org.assertj.core.api.Assertions.assertThat;

class AlbumWithArtistMappingConfigTest extends MappingTest {

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