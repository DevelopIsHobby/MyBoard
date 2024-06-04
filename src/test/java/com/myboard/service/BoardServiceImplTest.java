package com.myboard.service;

import com.myboard.dto.BoardDTO;
import com.myboard.dto.PageRequestDTO;
import com.myboard.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardServiceImplTest {
    @Autowired
    private BoardService boardService;

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(9).build();
        PageResultDTO<BoardDTO, Object[]> resultDTO = boardService.getList(pageRequestDTO);

        for(BoardDTO boardDTO : resultDTO.getDtoList()) {
            System.out.println(boardDTO);
        }
    }

//    @Test
//    public void getListAndTagsTest() {
//        boardService.getList().forEach(boardDTO -> {
//            System.out.println(boardService.getTags(boardDTO));
//        });
//    }
}