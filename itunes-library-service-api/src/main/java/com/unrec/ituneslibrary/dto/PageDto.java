package com.unrec.ituneslibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDto<T> {

    private List<T> data;
    private Long totalCount;

    public static <T> PageDto<T> of(List<T> data, Long total) {
        return new PageDto<>(data, total);
    }
}
