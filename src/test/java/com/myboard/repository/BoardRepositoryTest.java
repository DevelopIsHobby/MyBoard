package com.myboard.repository;

import com.myboard.entity.Board;
import com.myboard.entity.BoardTagMap;
import com.myboard.entity.Member;
import com.myboard.entity.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardTagMapRepository boardTagMapRepository;

    @Autowired
    private TagRepository tagRepository;

    @Test
    public void selectTags() {
        List<Object[]> list = boardTagMapRepository.getBoardWithTag(10l);
        for(Object[] arr : list) {
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void insertTag() {
        Tag tag1 = Tag.builder()
                .name("Backend").build();

        Tag tag2 = Tag.builder()
                .name("Devops").build();

        tagRepository.save(tag1);
        tagRepository.save(tag2);
    }

    @Test
    public void insertTags() {
        BoardTagMap boardTagMap = BoardTagMap.builder().tag(Tag.builder().tagnum(1l).build()).board(Board.builder().bno(10l).build()).build();

        boardTagMapRepository.save(boardTagMap);
//        IntStream.rangeClosed(1,100).forEach(i -> {
//            BoardTagMap boardTagMap = BoardTagMap.builder()
//                    .tag(tag1)
//                    .tag(tag2)
//                    .board(Board.builder().bno((long) i).build()).build();
//            boardTagMapRepository.save(boardTagMap);
//        });
    }

    @Test
    public void insertBoard() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder().email("user"+i+"@aaa.com").build();

            Board board = Board.builder()
                    .title("Title..."+i)
                    .content("Content..."+i)
                    .writer(member)
                    .build();

            boardRepository.save(board);
        });
    }

    @Test
    public void testWithReviewCount() {
        Long bno =(Long) 10l;

        List<Object[]> result = boardRepository.getBoardWithReplyCount(bno);

        for(Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }
}