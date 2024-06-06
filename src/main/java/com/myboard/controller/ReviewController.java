package com.myboard.controller;

import com.myboard.dto.ReviewDTO;
import com.myboard.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews/")
@Log4j2
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping(value="/{bno}/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("bno") Long bno) {
        log.info("getList_bno : " + bno);

        List<ReviewDTO> reviewDTOList = reviewService.getListReviewsByBoard(bno);

        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
    }

    @PostMapping("/{bno}")
    public ResponseEntity<Long> addReview(@RequestBody ReviewDTO reviewDTO) {
        log.info("addReivewDTO : " + reviewDTO);

        Long rno = reviewService.register(reviewDTO);

        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    @DeleteMapping("/{bno}/{rno}")
    public ResponseEntity<Long> remove(@PathVariable Long rno) {
        log.info("remove_rno : " + rno);

        reviewService.remove(rno);

        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    @PutMapping("/{bno}/{rno}")
    public ResponseEntity<Long> modifyReview(@PathVariable Long rno, @RequestBody ReviewDTO reviewDTO) {
        log.info("modifiy_reivewDTO : " + reviewDTO);

        reviewService.modify(reviewDTO);

        return new ResponseEntity<>(rno, HttpStatus.OK);
    }
}
