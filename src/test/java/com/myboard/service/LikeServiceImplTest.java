package com.myboard.service;

import com.myboard.dto.LikeRequestDTO;
import com.myboard.entity.Board;
import com.myboard.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LikeServiceImplTest {
    @Autowired
    LikeService likeService;

    @Test
    void insert() throws Exception {
        Board board_56 = Board.builder().bno(56l).build();
        Member member_10 = Member.builder().email("user10@aaa.com").build();
        LikeRequestDTO likeRequestDTO = LikeRequestDTO.builder()
                .board_bno(board_56.getBno())
                .member_email(member_10.getEmail())
                .build();

        likeService.insert(likeRequestDTO);
    }

    @Test
    public void delete() throws Exception{
        Board board_56 = Board.builder().bno(56l).build();
        Member member_10 = Member.builder().email("user10@aaa.com").build();
        LikeRequestDTO likeRequestDTO = LikeRequestDTO.builder()
                .board_bno(board_56.getBno())
                .member_email(member_10.getEmail())
                .build();

        likeService.delete(likeRequestDTO);
    }
}