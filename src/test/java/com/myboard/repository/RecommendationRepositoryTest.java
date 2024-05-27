package com.myboard.repository;

import com.myboard.entity.Board;
import com.myboard.entity.Member;
import com.myboard.entity.Recommendation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
class RecommendationRepositoryTest {
    @Autowired
    private RecommendationRepository recommendationRepository;


    @Test
    public void likeTest() {
        recommendationRepository.deleteAll();
        Board board = Board.builder().bno(10l).build();
        Member member = Member.builder().email("user10@aaa.com").build();

        Recommendation recommendation = Recommendation.builder().board(board).writer(member).build();
        recommendationRepository.save(recommendation);

        boolean bool = recommendationRepository.existsByBoardAndWriter(board, member);
        System.out.println("bool = " + bool);

        if(board.getLikeCount() ==null) {
            board.setLikeCount(1);
        } else {
            board.setLikeCount(board.getLikeCount()+1);
        }

        System.out.println("board.getLikeCount() = " + board.getLikeCount());

    }
    @Test
    public void likeCancelTest() {
        recommendationRepository.deleteAll();
        Board board = Board.builder().bno(10l).build();
        Member member = Member.builder().email("user10@aaa.com").build();

        Recommendation recommendation = Recommendation.builder().board(board).writer(member).build();
        recommendationRepository.save(recommendation);

        boolean bool = recommendationRepository.existsByBoardAndWriter(board, member);
        System.out.println("bool = " + bool);

        if(board.getLikeCount() ==null) {
            board.setLikeCount(1);
        } else {
            board.setLikeCount(board.getLikeCount()+1);
        }

        System.out.println("board.getLikeCount() = " + board.getLikeCount());

        recommendationRepository.recommendationCancel(board, member);

        board.setLikeCount(board.getLikeCount() - 1 );

        System.out.println("board.getLikeCount() = " + board.getLikeCount());

        bool = recommendationRepository.existsByBoardAndWriter(board, member);

        System.out.println("bool = " + bool);
    }
}