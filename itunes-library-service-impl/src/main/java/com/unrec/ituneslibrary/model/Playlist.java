package com.unrec.ituneslibrary.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "PLAYLISTS")
public class Playlist {
    @Id
    private String id;

    @Column
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "tracks_in_playlist",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id"))
    private List<Track> tracks;

    @PreRemove
    private void deleteFromTracks() {
        tracks.forEach(track -> track.getPlaylists().remove(this));
    }

    @Column
    private Boolean hasAllItems;
    @Column
    private Boolean isSmart;
    @Column
    private Boolean isMaster;

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
        Playlist other = (Playlist) obj;
        if (id == null) {
            return false;
        } else return id.equals(other.getId());
    }


}