package com.unrec.ituneslibrary.parser.utils;

import com.unrec.ituneslibrary.parser.dom.Library;
import lombok.experimental.UtilityClass;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@UtilityClass
public class TestObjects {

    public static final String SAMPLE_LIBRARY_PATH = "src/test/resources/sample-library.xml";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public static Library getTestLibrary() throws MalformedURLException, ParseException {
        return new Library()
                .setMajorVersion(1)
                .setMinorVersion(1)
                .setDate(DATE_FORMAT.parse("2020-09-29T15:14:05Z"))
                .setApplicationVersion("12.10.9.3")
                .setFeatures(5)
                .setShowContentRatings(true)
                .setMusicFolder(new URL("file://localhost/D:/music/!itunes/iTunes%20Media/iTunes%20Media/"))
                .setLibraryId("DFB4D882E87C2F12")
                ;
    }
}