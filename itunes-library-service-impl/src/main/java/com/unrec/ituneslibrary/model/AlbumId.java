package com.unrec.ituneslibrary.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class AlbumId implements Serializable {
    @Column(name = "album_name")
    private String name;
    @Column(name = "album_year")
    private Integer year;

    @Override
    public int hashCode() {
        return 42;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        AlbumId other = (AlbumId) obj;
        if (!Objects.equals(name, other.name)) return false;
        return Objects.equals(year, other.year);
    }
}