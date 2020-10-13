package com.unrec.ituneslibrary.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "TRACKS")
public class Track implements Serializable {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToMany(mappedBy = "tracks", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<Playlist> playlists;

    @PreRemove
    private void deleteFromPlaylists() {
        playlists.forEach(playlist -> playlist.getTracks().remove(this));
    }

    @Column
    private String name;
    @Column
    private String genre;
    @Column
    private String composer;
    @Column
    private Integer trackNumber;
    @Column
    private Integer discNumber;
    @Column
    private Boolean explicit;
    @Column
    private LocalDateTime releaseDate;

    /* File properties */
    @Column
    private URL location;
    @Column
    private Long size;
    @Column
    private String kind;
    @Column
    private Integer bitRate;
    @Column
    private Integer sampleRate;
    @Column
    private Integer bpm;
    @Column
    private String comments;

    /* Library-related properties */
    @Column
    private Integer trackId;
    @Column
    private Integer playCount;
    @Column
    private Integer skipCount;
    @Column
    private Integer rating;
    @Column
    private LocalDateTime dateAdded;
    @Column
    private LocalDateTime dateModified;
    @Column
    private LocalDateTime playDateUTC;
    @Column
    private LocalDateTime skipDate;
    @Column
    private Boolean purchased;
    @Column
    private String trackType;

    @Override
    public int hashCode() {
        return 42;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Track other = (Track) obj;
        if (id == null) {
            return false;
        } else return id.equals(other.getId());
    }
}