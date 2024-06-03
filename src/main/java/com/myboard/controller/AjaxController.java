package com.myboard.controller;

import com.myboard.dto.BoardDTO;
import com.myboard.dto.PageRequestDTO;
import com.myboard.dto.PageResultDTO;
import com.myboard.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j2
@AllArgsConstructor
public class AjaxController {
    private final BoardService boardService;

    @GetMapping("/api/data")
    public ResponseEntity<PageResultDTO<BoardDTO, Object[]>> list(PageRequestDTO pageRequestDTO) {
        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        List<BoardDTO> resultWithTags = new ArrayList<>();
        for(BoardDTO boardDTO : result.getDtoList()){
            resultWithTags.add(boardService.getTags(boardDTO));
        }
        result.setDtoList(resultWithTags);
        System.out.println("result = " + result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
