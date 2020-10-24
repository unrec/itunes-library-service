package com.unrec.ituneslibrary.service;

import com.unrec.ituneslibrary.exception.NotFoundException;
import com.unrec.ituneslibrary.model.Track;
import com.unrec.ituneslibrary.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TrackService {

    private final MapperFacade mapperFacade;
    private final TrackRepository trackRepository;

    @Transactional(readOnly = true)
    public Track getById(String id) {
        return trackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, Track.class));
    }

    public Page<Track> getAll(Pageable requiredPages) {
        return trackRepository.findAll(requiredPages);
    }

    @Transactional(readOnly = true)
    public Track getByAttributes(String artist, String name, String album) {
        return trackRepository.findByAttributes(artist, name, album)
                .orElseThrow(() -> new NotFoundException(String.format("Track %s - %s (%s) not found", artist, name, album)));
    }

    @Transactional(readOnly = true)
    public Page<Track> getByRating(Integer rating, Pageable requiredPages) {
        return trackRepository.findAllByRating(rating, requiredPages);
    }
}