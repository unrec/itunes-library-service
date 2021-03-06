package com.unrec.ituneslibrary.parser.dom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.net.URL;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackRecord {

    private String album;
    private String albumArtist;
    private Integer albumRating;
    private Boolean albumRatingComputed;
    private String artist;
    private Integer artworkCount;
    private Integer bitRate;
    private Integer bpm;
    private Boolean clean;
    private String comments;
    private Boolean compilation;
    private String composer;
    private LocalDateTime dateAdded;
    private LocalDateTime dateModified;
    private Boolean disabled;
    private Integer discCount;
    private Integer discNumber;
    private String episode;
    private Integer episodeOrder;
    private String equalizer;
    private Boolean explicit;
    private Integer fileFolderCount;
    private Long fileType;
    private String genre;
    private String grouping;
    private String kind;
    private Integer libraryFolderCount;
    private URL location;
    private Boolean loved;
    private Boolean movie;
    private String name;
    private Integer normalization;
    private Boolean partOfGaplessAlbum;
    private String persistentID;
    private Integer playCount;
    private Long playDate;
    private LocalDateTime playDateUTC;
    private Boolean podcast;
    private Boolean purchased;
    private Integer rating;
    private LocalDateTime releaseDate;
    private Integer sampleRate;
    private Integer season;
    private String series;
    private Long size;
    private Integer skipCount;
    private LocalDateTime skipDate;
    private String sortAlbum;
    private String sortAlbumArtist;
    private String sortArtist;
    private String sortComposer;
    private String sortName;
    private String sortSeries;
    private Long startTime;
    private Long stopTime;
    private Long totalTime;
    private Integer trackCount;
    private Integer trackID;
    private Integer trackNumber;
    private String trackType;
    private Integer volumeAdjustment;
    private Integer year;
}