package com.myboard.service;

import com.myboard.dto.ReviewDTO;
import com.myboard.entity.Board;
import com.myboard.entity.Member;
import com.myboard.entity.Review;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getListReviewsByBoard(Long bno);

    Long register(ReviewDTO reviewDTO);

    void modify(ReviewDTO reviewDTO);

    void remove(Long rno);

    //reviewDTO를 review객체로 변환, Board객체의 처리 수반
    default Review dtoToEntity(ReviewDTO reviewDTO) {
        Review boardReview = Review.builder()
                .rno(reviewDTO.getRno())
                .text(reviewDTO.getText())
                .board(Board.builder().bno(reviewDTO.getBno()).build())
                .reviewer(Member.builder().email(reviewDTO.getEmail()).build())
                .build();

        return boardReview;
    }


    // review객체를 reviewDTO로 변환, Board객체가 필요하지 않으므로 게시물 번호만
    default ReviewDTO entityToDTO(Review review) {
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .rno(review.getRno())
                .bno(review.getRno())
                .email(review.getReviewer().getEmail())
                .text(review.getText())
                .regDate(review.getRegDate())
                .modDate(review.getModDate())
                .build();

        return reviewDTO;
    }
}
