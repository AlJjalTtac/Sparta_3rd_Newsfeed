/*package com.example.sparta_3rd_newsfeed.friend.dto.response;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter

public class PageResponseDto<T> {
    private final int pageNumber;
    private final int pageSize;
    private final int totalPages;
    private final long totalElements;
    private final List<T> content;

    public PageResponseDto(Page<T> page) {
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.content = page.getContent();
    }
}*/
