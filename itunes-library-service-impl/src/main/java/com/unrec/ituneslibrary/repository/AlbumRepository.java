package com.unrec.ituneslibrary.repository;

import com.unrec.ituneslibrary.model.Album;
import com.unrec.ituneslibrary.model.AlbumId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, AlbumId> {
}
