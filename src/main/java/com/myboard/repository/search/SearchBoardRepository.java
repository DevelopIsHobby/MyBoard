package com.myboard.repository.search;

import com.myboard.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchBoardRepository {
    Board search();

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);

    Page<Object[]> findBoardsByTagName(String tagName);
}
