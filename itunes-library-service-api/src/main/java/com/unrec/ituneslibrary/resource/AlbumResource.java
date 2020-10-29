package com.unrec.ituneslibrary.resource;

import com.unrec.ituneslibrary.dto.AlbumResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/library/albums")
public interface AlbumResource {

    @GetMapping("/find")
    @Operation(description = "Return the album by its name and artist")
    @ResponseStatus(HttpStatus.OK)
    AlbumResponseDto getOne(
            @RequestParam(value = "artist") String artist,
            @RequestParam(value = "album") String album);
}