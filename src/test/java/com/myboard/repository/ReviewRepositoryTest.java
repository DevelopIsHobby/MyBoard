package com.myboard.repository;

import com.myboard.entity.Board;
import com.myboard.entity.Member;
import com.myboard.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;


    @Test
    public void deletetest() {Board board = Board.builder().bno(10l).build();
        List<Review> result = reviewRepository.findByBoard(board);
        System.out.println("Before delete ===================");
        result.forEach(review -> {
            System.out.println(review.getRno());
            System.out.println(review.getReviewer().getEmail());
            System.out.println(review.getText());
        });
        System.out.println("=================================");
        Member member = Member.builder().email("user39@aaa.com").build();

        reviewRepository.deleteByMember(member, 10l);
        Board board2 = Board.builder().bno(10l).build();

        List<Review> result2 = reviewRepository.findByBoard(board2);
        System.out.println("After delete=======================");
        result2.forEach(review -> {
            System.out.println(review.getRno());
            System.out.println(review.getReviewer().getEmail());
            System.out.println(review.getText());
        });
        System.out.println("=================================");
    }

    @Test
    public void findByBoard() {
        Board board = Board.builder().bno(10l).build();
        List<Review> result = reviewRepository.findByBoard(board);

        result.forEach(review -> {
            System.out.println(review.getRno());
            System.out.println(review.getReviewer().getEmail());
            System.out.println(review.getText());
        });
    }
    @Test
    public void insertReviews() {
        IntStream.rangeClosed(1,300).forEach(i -> {
            long bno = (long) (Math.random() * 100) + 1;
            long mno = (long) (Math.random() * 100) + 1;

            Member member = Member.builder().email("user"+mno+"@aaa.com").build();
            Board board = Board.builder().bno(bno).build();

            Review review = Review.builder()
                    .text("Review..."+i)
                    .reviewer(member)
                    .board(board)
                    .build();

            reviewRepository.save(review);
        });
    }
}