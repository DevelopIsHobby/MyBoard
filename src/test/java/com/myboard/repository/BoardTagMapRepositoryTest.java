package com.myboard.repository;

import com.myboard.entity.Board;
import com.myboard.entity.BoardTagMap;
import com.myboard.entity.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BoardTagMapRepositoryTest {
    @Autowired
    private BoardTagMapRepository boardTagMapRepository;
    @Test
    void findBoardTagMapByBoardAndTag() {
        Board board = Board.builder().bno(10l).build();
        Tag tag = Tag.builder().name("Backend").build();
        BoardTagMap boardTagMap = boardTagMapRepository.findBoardTagMapByBoardAndTag(board, tag.getName());

        System.out.println(boardTagMap);
    }
}