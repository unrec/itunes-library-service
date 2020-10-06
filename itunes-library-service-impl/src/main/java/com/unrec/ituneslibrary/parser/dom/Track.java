package com.unrec.ituneslibrary.parser.dom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.net.URL;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Track {

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
    private Date dateAdded;
    private Date dateModified;
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
    private String name;
    private Integer normalization;
    private Boolean partOfGaplessAlbum;
    private String persistentID;
    private Integer playCount;
    private Long playDate;
    private Date playDateUTC;
    private Boolean podcast;
    private Boolean purchased;
    private Integer rating;
    private Date releaseDate;
    private Integer sampleRate;
    private Integer season;
    private String series;
    private Long size;
    private Integer skipCount;
    private Date skipDate;
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