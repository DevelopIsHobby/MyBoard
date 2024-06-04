package com.myboard.dto;

import com.myboard.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PageResultDTOTest {
    @Autowired
    private BoardService boardService;

    @Test
    public void testList2() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(9).build();

        PageResultDTO<BoardDTO, Object[]> resultDTO = boardService.getList(pageRequestDTO);

        System.out.println("PREV : " + resultDTO.isPrev());
        System.out.println("NEXT : " + resultDTO.isNext());
        System.out.println("TOTAL : " + resultDTO.getTotalPage());

        System.out.println("=======================================");
        for(BoardDTO boardDTO : resultDTO.getDtoList()) {
            System.out.println(boardDTO);
        }

        System.out.println("=======================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }
}