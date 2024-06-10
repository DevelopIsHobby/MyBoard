package com.myboard.service;

import com.myboard.dto.BoardDTO;
import com.myboard.dto.PageRequestDTO;
import com.myboard.dto.PageResultDTO;
import com.myboard.entity.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BoardServiceImplTest {
    @Autowired
    private BoardService boardService;


    @Test
    public void getListMyPageTest() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(0)
                .size(9)
                .build();

        PageResultDTO<BoardDTO, Object[]> resultDTO = boardService.getListMyPage(pageRequestDTO);

        List<BoardDTO> resultWithTags = new ArrayList<>();
        for(BoardDTO boardDTO : resultDTO.getDtoList()){
            resultWithTags.add(boardService.getTags(boardDTO));
        }
        resultDTO.setDtoList(resultWithTags);



        System.out.println("Prev : " + resultDTO.isPrev());
        System.out.println("Next : " + resultDTO.isNext());
        System.out.println("Total : " + resultDTO.getTotalPage());

        System.out.println("===============================");
        for(BoardDTO boardDTO : resultDTO.getDtoList()) {
            System.out.println(boardDTO);
        }
        System.out.println("===============================");
        resultDTO.getPageList().forEach(i-> {
            System.out.println( i + " ");
        });
    }

    @Test
    public void controllerListTestWithTags() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(9)
                .build();

        PageResultDTO<BoardDTO, Object[]> resultDTO = boardService.getListMyPage(pageRequestDTO);

        List<BoardDTO> resultWithTags = new ArrayList<>();
        for(BoardDTO boardDTO : resultDTO.getDtoList()){
            resultWithTags.add(boardService.getTags(boardDTO));
        }
        resultDTO.setDtoList(resultWithTags);



        System.out.println("Prev : " + resultDTO.isPrev());
        System.out.println("Next : " + resultDTO.isNext());
        System.out.println("Total : " + resultDTO.getTotalPage());

        System.out.println("===============================");
        for(BoardDTO boardDTO : resultDTO.getDtoList()) {
            System.out.println(boardDTO);
        }
        System.out.println("===============================");
        resultDTO.getPageList().forEach(i-> {
            System.out.println( i + " ");
        });
    }
    @Test
    public void controllerListTest() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(9)
                .type("tc")
                .keyword("test")
                .build();

        PageResultDTO<BoardDTO, Object[]> resultDTO = boardService.getList(pageRequestDTO);

        List<BoardDTO> resultWithTags = new ArrayList<>();
        for(BoardDTO boardDTO : resultDTO.getDtoList()){
            resultWithTags.add(boardService.getTags(boardDTO));
        }
        resultDTO.setDtoList(resultWithTags);



        System.out.println("Prev : " + resultDTO.isPrev());
        System.out.println("Next : " + resultDTO.isNext());
        System.out.println("Total : " + resultDTO.getTotalPage());

        System.out.println("===============================");
        for(BoardDTO boardDTO : resultDTO.getDtoList()) {
            System.out.println(boardDTO);
        }
        System.out.println("===============================");
        resultDTO.getPageList().forEach(i-> {
            System.out.println( i + " ");
        });
    }

    @Test
    public void testLikeCnt() {
        Board boforeBoard = boardService.getBoardByBno(97l);
        System.out.println("Before board.getLikeCount() = " + boforeBoard.getLikeCount());
        boardService.updateCount(boforeBoard, true);
        Board afterBoard = boardService.getBoardByBno(97l);
        System.out.println("After board.getLikeCount() = " + afterBoard.getLikeCount());
    }

    @Test
    public void testModify() {
        List<String> tags = new ArrayList<>();
        tags.add("#Changed");
        tags.add("#Modified");

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(99l)
                .writerEmail("user99@aaa.com")
                .title("제목을 변경합니다.")
                .content("내용을 변경합니다.")
                .tags(tags)
                .build();

        boardService.modifyBoards(boardDTO);
    }

    @Test
    public void testRemove() {
        boardService.removeBoards(102l);
    }

    @Test
    public void getTest() {
        BoardDTO boardDTO = boardService.getBoard(11l);

        System.out.println(boardDTO);
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(9)
                .type("tc")
                .keyword("test")
                .build();

        PageResultDTO<BoardDTO, Object[]> resultDTO = boardService.getList(pageRequestDTO);

        System.out.println("Prev : " + resultDTO.isPrev());
        System.out.println("Next : " + resultDTO.isNext());
        System.out.println("Total : " + resultDTO.getTotalPage());

        System.out.println("===============================");
        for(BoardDTO boardDTO : resultDTO.getDtoList()) {
            System.out.println(boardDTO);
        }
        System.out.println("===============================");
        resultDTO.getPageList().forEach(i-> {
            System.out.println( i + " ");
        });
    }

//    @Test
//    public void getListAndTagsTest() {
//        boardService.getList().forEach(boardDTO -> {
//            System.out.println(boardService.getTags(boardDTO));
//        });
//    }
}