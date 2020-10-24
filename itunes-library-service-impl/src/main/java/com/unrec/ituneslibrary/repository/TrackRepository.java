package com.unrec.ituneslibrary.repository;

import com.unrec.ituneslibrary.model.Track;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TrackRepository extends JpaRepository<Track, String> {

    @Query(value = "SELECT * FROM TRACKS t " +
            "WHERE t.artist_name = ?1 " +
            "AND t.name = ?2 " +
            "AND t.album_name = ?3",
            nativeQuery = true)
    Optional<Track> findByAttributes(String artist, String name, String album);

    Page<Track> findAllByRating(Integer rating, Pageable pageable);
}
