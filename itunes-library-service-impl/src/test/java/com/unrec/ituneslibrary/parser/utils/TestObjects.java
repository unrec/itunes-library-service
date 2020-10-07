package com.unrec.ituneslibrary.parser.utils;

import com.unrec.ituneslibrary.parser.dom.Library;
import lombok.experimental.UtilityClass;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class TestObjects {

    public static final String SAMPLE_LIBRARY_PATH = "src/test/resources/sample-library.xml";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public static Library getTestLibrary() throws MalformedURLException {
        return new Library()
                .setMajorVersion(1)
                .setMinorVersion(1)
                .setDate(LocalDateTime.parse("2020-09-29T15:14:05Z", DATE_TIME_FORMATTER))
                .setApplicationVersion("12.10.9.3")
                .setFeatures(5)
                .setShowContentRatings(true)
                .setMusicFolder(new URL("file://localhost/D:/music/!itunes/iTunes%20Media/iTunes%20Media/"))
                .setLibraryId("DFB4D882E87C2F12");
    }
}