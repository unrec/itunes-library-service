package com.unrec.ituneslibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackResponseDto {

    private String id;
    private String name;
    private String artist;
    private String album;
    private String genre;

    private Integer trackNumber;
    private Integer discNumber;

    private Long size;
    private Integer bitRate;

    private Integer playCount;
    private Integer rating;
    private LocalDateTime dateAdded;
}