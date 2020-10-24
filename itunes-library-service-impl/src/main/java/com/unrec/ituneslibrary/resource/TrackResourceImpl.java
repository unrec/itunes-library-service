package com.unrec.ituneslibrary.resource;

import com.ituneslibrary.dto.PageDto;
import com.ituneslibrary.dto.TrackResponseDto;
import com.ituneslibrary.resource.TrackResource;
import com.unrec.ituneslibrary.model.Track;
import com.unrec.ituneslibrary.service.TrackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TrackResourceImpl implements TrackResource {

    private final MapperFacade mapperFacade;
    private final TrackService trackService;

    @Override
    public TrackResponseDto getOne(String id) {
        log.debug("getOne() - start: id = {}", id);

        var track = trackService.getById(id);
        var result = mapperFacade.map(track, TrackResponseDto.class);

        log.debug("getOne() - end: result = {}", result);
        return result;
    }

    @Override
    public PageDto<TrackResponseDto> getPage(Integer size, Integer page) {
        log.debug("getPage() - start: size = {}, page = {}", size, page);

        Pageable requiredPages = PageRequest.of(page, size);
        Page<Track> tracks = trackService.getAll(requiredPages);
        var result = mapperFacade.mapAsList(tracks.getContent(), TrackResponseDto.class);

        log.debug("getPage() - end: result = {}", result);
        return PageDto.of(result, tracks.getTotalElements());
    }

    @Override
    public TrackResponseDto getOne(String artist, String name, String album) {
        log.debug("getOne() - start: artist = {}, name = {}, album = {}", artist, name, album);

        var track = trackService.getByAttributes(artist, name, album);
        var result = mapperFacade.map(track, TrackResponseDto.class);
        log.debug("getOne() - end: result = {}", result);
        return result;
    }

    @Override
    public PageDto<TrackResponseDto> getByRating(Integer rating, Integer size, Integer page) {
        log.debug("getByRating() - start: rating = {}, size = {}, page = {}", rating, size, page);

        Pageable requiredPages = PageRequest.of(page, size);
        Page<Track> tracks = trackService.getByRating(rating, requiredPages);
        var result = mapperFacade.mapAsList(tracks, TrackResponseDto.class);

        log.debug("getByRating() - end: result = {}", result);
        return PageDto.of(result, tracks.getTotalElements());
    }
}