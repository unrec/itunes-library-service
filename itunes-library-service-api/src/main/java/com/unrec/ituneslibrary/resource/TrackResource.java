package com.unrec.ituneslibrary.resource;

import com.unrec.ituneslibrary.dto.PageDto;
import com.unrec.ituneslibrary.dto.TrackResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/library/tracks")
public interface TrackResource {

    @GetMapping("/{id}")
    @Operation(description = "Return the track with provided id")
    @ResponseStatus(HttpStatus.OK)
    TrackResponseDto getOne(@PathVariable("id") String id);

    @GetMapping("/find")
    @Operation(description = "Return the track with provided data")
    @ResponseStatus(HttpStatus.OK)
    TrackResponseDto getOne(
            @RequestParam(value = "artist") String artist,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "album") String album);

    @GetMapping
    @Operation(description = "Return page of tracks")
    @ResponseStatus(HttpStatus.OK)
    PageDto<TrackResponseDto> getPage(
            @RequestParam(value = "size", required = false, defaultValue = "${itunes-library-service.pagination.defaultSize}") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "${itunes-library-service.pagination.defaultPage}") Integer page);


    @GetMapping("/rating/{rating}")
    @Operation(description = "Return page of tracks by its rating")
    @ResponseStatus(HttpStatus.OK)
    PageDto<TrackResponseDto> getByRating(
            @PathVariable("rating") Integer rating,
            @RequestParam(value = "size", required = false, defaultValue = "${itunes-library-service.pagination.defaultSize}") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "${itunes-library-service.pagination.defaultPage}") Integer page);

}