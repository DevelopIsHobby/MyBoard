package com.myboard.service;

import com.myboard.dto.ReviewDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReviewServiceImplTest {
    @Autowired
    private ReviewService reviewService;

    @Test
    public void testGetList() {
        Long bno = 11l;
        List<ReviewDTO> reviewDTOList = reviewService.getListReviewsByBoard(bno);

        reviewDTOList.forEach( reviewDTO -> {
            System.out.println(reviewDTO);
        });
    }
}