package com.myboard.service;

import com.myboard.dto.ScrapDTO;

import java.util.List;

public interface ScrapService {
    void add(ScrapDTO scrapDTO) throws Exception;

    void cancel(ScrapDTO scrapDTO) throws Exception;

    List<Object[]> getScrapLists();
}
