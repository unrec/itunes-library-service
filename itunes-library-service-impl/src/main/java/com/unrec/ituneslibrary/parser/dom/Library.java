package com.unrec.ituneslibrary.parser.dom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Library {

    private Integer majorVersion;
    private Integer minorVersion;
    private LocalDateTime date;
    private String applicationVersion;
    private Integer features;
    private Boolean showContentRatings;
    private URL musicFolder;
    private String libraryId;

    private List<TrackRecord> trackRecords;
    private List<PlaylistRecord> playlistRecords;
}