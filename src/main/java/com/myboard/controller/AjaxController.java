package com.myboard.controller;

import com.myboard.dto.BoardDTO;
import com.myboard.dto.LikeRequestDTO;
import com.myboard.dto.PageRequestDTO;
import com.myboard.dto.PageResultDTO;
import com.myboard.entity.Board;
import com.myboard.service.BoardService;
import com.myboard.service.LikeService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j2
@AllArgsConstructor
public class AjaxController {
    private final BoardService boardService;
    private final LikeService likeService;


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

    // @ResponseBody로 전달받으려면 JSON 데이터로 전달해야하고
    // @RequestParam으로 전달받으려면 URL을 통해 전달받아야 한다.
    @PostMapping("/like")
    public ResponseEntity<Long> insert(@RequestBody LikeRequestDTO likeRequestDTO, @RequestParam Long likeCount) {
        try {
            log.info("AjaxController.............../like");
            likeService.insert(likeRequestDTO);
            likeCount++;
            Board board = Board.builder().bno(likeRequestDTO.getBoard_bno()).build();
            boardService.updateCount(board, true);
            return new ResponseEntity<>(likeCount, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/disLike")
    public ResponseEntity<?> delete(@RequestBody LikeRequestDTO likeRequestDTO, @RequestParam Long likeCount) {
        try {
            log.info("AjaxController............/disLike");
            likeService.delete(likeRequestDTO);
            likeCount--;
            Board board = Board.builder().bno(likeRequestDTO.getBoard_bno()).build();
            boardService.updateCount(board, false);
            return new ResponseEntity<>(likeCount, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
