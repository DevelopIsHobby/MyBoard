package com.myboard.controller;

import com.myboard.dto.*;
import com.myboard.entity.Board;
import com.myboard.entity.Member;
import com.myboard.service.BoardService;
import com.myboard.service.LikeService;
import com.myboard.service.ScrapService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@AllArgsConstructor
public class AjaxController {
    private final BoardService boardService;
    private final LikeService likeService;
    private final ScrapService scrapService;

    // 무한 스크롤 Ajax
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

    // 추천 기능 Ajax
    // @ResponseBody로 전달받으려면 JSON 데이터로 전달해야하고
    // @RequestParam으로 전달받으려면 URL을 통해 전달받아야 한다.
    @PostMapping("/like")
    public ResponseEntity<Long> insert(@RequestBody LikeRequestDTO likeRequestDTO, @RequestParam Long likeCount) {
        log.info("AjaxController.............../like");
        try {
            likeService.insert(likeRequestDTO);
            likeCount++;
            Board board = Board.builder().bno(likeRequestDTO.getBoard_bno()).build();
            boardService.updateCount(board, true);
            return new ResponseEntity<>(likeCount, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 추천 기능 Ajax
    @DeleteMapping("/disLike")
    public ResponseEntity<?> delete(@RequestBody LikeRequestDTO likeRequestDTO, @RequestParam Long likeCount) {
        log.info("AjaxController............/disLike");
        try {
            likeService.delete(likeRequestDTO);
            likeCount--;
            Board board = Board.builder().bno(likeRequestDTO.getBoard_bno()).build();
            boardService.updateCount(board, false);
            return new ResponseEntity<>(likeCount, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Scrap Ajax
    @PostMapping("/scrap")
    public ResponseEntity<Map<String, String>> scrap(@RequestParam Long bno, @RequestParam String writer) {
        System.out.println("bno = " + bno);
        System.out.println("writer = " + writer);
        Board board = Board.builder().bno(bno).build();
        Member member = Member.builder().email(writer).build();

        ScrapDTO scrapDTO = ScrapDTO.builder().board(board).member(member).build();
        Map<String, String> response = new HashMap<>();
        try {
            scrapService.add(scrapDTO);
            response.put("status", "success");
            response.put("msg", "Board scrapped successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Scrap Error......");
            throw new RuntimeException(e);
        }
    }

    // Scrap Ajax
    @DeleteMapping("/cancelScrap")
    public ResponseEntity<Map<String, String>> unscrapped(@RequestParam Long bno, @RequestParam String writer) {
        Board board = Board.builder().bno(bno).build();
        Member member = Member.builder().email(writer).build();

        ScrapDTO scrapDTO = ScrapDTO.builder().board(board).member(member).build();
        Map<String, String> response = new HashMap<>();
        try {
            scrapService.cancel(scrapDTO);
            response.put("status", "success");
            response.put("msg", "Board unscrapped successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Unscrap Error......");
            throw new RuntimeException(e);
        }
    }

}
