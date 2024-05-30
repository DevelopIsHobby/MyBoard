package com.myboard.service;

import com.myboard.dto.BoardDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BoardServiceImplTest {
    @Autowired
    private BoardService boardService;

    @Test
    public void getListAndTagsTest() {
        boardService.getList().forEach(boardDTO -> {
            System.out.println(boardService.getTags(boardDTO));
        });
    }
}