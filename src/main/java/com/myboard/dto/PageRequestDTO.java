package com.myboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.data.domain.Pageable;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
    private int page;
    private int size;
    public PageRequestDTO() {
        this.page = 1;;
        this.size = 9;
    }
    public Pageable getPageable(Sort sort) {
        return (Pageable) PageRequest.of(page-1, size, sort);
    }
}
