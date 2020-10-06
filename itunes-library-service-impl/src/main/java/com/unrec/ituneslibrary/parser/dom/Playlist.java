package com.unrec.ituneslibrary.parser.dom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {

    private Boolean allItems;
    private Boolean audiobooks;
    private Integer distinguishedKind;
    private Boolean folder;
    private Boolean master;
    private Boolean movies;
    private Boolean music;
    private String name;
    private String parentPersistentID;
    private Integer playlistID;
    private Map<Integer, Track> playlistItems;
    private String playlistPersistentID;
    private Boolean podcasts;
    private String smartCriteria;
    private String smartInfo;
    private Boolean tvShows;
    private Boolean visible;
}