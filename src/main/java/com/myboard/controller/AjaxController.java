package com.myboard.controller;

import com.myboard.dto.*;
import com.myboard.entity.Board;
import com.myboard.entity.Member;
import com.myboard.service.BoardService;
import com.myboard.service.LikeService;
import com.myboard.service.ScrapService;
import jakarta.transaction.Transactional;
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
//        System.out.println("result = " + result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 추천 기능 Ajax
    // @ResponseBody로 전달받으려면 JSON 데이터로 전달해야하고
    // @RequestParam으로 전달받으려면 URL을 통해 전달받아야 한다.
    @PostMapping("/like")
    @Transactional
    public ResponseEntity<Integer> insert(@RequestBody LikeRequestDTO likeRequestDTO, @RequestParam("likeCount") Integer likeCount) {
        log.info("AjaxController.............../like");
        try {
            likeService.insert(likeRequestDTO);

            Board board = boardService.getBoardByBno(likeRequestDTO.getBoard_bno());
            System.out.println("before LikeCount = " + board.getLikeCount());
            boardService.updateCount(board, true);
//            Board afterBoard = boardService.getBoardByBno(likeRequestDTO.getBoard_bno());
            // 테스트에서는 위에서 처럼하면 before, after가 다르게 나왔는데 컨트롤러에선 계속 같은 값만 나오고 갱신이 안되네..
            likeCount++;
            System.out.println("after LikeCount = " + likeCount);

            return new ResponseEntity<>(likeCount, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // 추천 기능 Ajax
    @DeleteMapping("/disLike")
    @Transactional
    public ResponseEntity<Integer> delete(@RequestBody LikeRequestDTO likeRequestDTO, @RequestParam("likeCount") Integer likeCount) {
        log.info("AjaxController............/disLike");
        try {
            likeService.delete(likeRequestDTO);

            Board board = boardService.getBoardByBno(likeRequestDTO.getBoard_bno());
            System.out.println("before LikeCount = " + board.getLikeCount());

            boardService.updateCount(board, false);
//            Board afterBoard = boardService.getBoardByBno(likeRequestDTO.getBoard_bno());

            likeCount--;
            System.out.println("after LikeCount = " + likeCount);


            return new ResponseEntity<>(likeCount, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Scrap Ajax
    @PostMapping("/scrap")
    public ResponseEntity<Map<String, String>> scrap(@RequestParam("bno") Long bno, @RequestParam("writer") String writer) {
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
    public ResponseEntity<Map<String, String>> unscrapped(@RequestParam("bno") Long bno, @RequestParam("writer") String writer) {
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
