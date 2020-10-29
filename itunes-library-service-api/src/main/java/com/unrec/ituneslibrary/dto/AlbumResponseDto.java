package com.unrec.ituneslibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumResponseDto {
    private String name;
    private String artist;
    private Integer year;
    private List<TrackResponseDto> tracks;
    private Boolean compilation;
    private Map<Integer, Integer> trackDiscInfo;
}