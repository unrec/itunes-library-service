package com.unrec.ituneslibrary.repository;

import com.unrec.ituneslibrary.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, String> {
}
