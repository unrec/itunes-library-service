package com.unrec.ituneslibrary.repository;

import com.unrec.ituneslibrary.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, String> {
}
