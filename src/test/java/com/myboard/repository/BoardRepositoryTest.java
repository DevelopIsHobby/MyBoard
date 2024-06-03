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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
@SpringBootTest
class

BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardTagMapRepository boardTagMapRepository;

    @Autowired
    private TagRepository tagRepository;

//    @Test
//    public void getListwithTagTest() {
//        List<Object[]> result = boardRepository.getListWithTags();
//        for (Object[] arr: result) {
//            System.out.println(Arrays.toString(arr));
//        }
//    }
    @Test
    public void getBoardbyBno() {
        List<Object[]> result = boardRepository.getBoardbyBno(10l);

        for(Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }

    }

    @Test
    public void getListTags() {
        Object[] result = boardRepository.getTagByBno(10l);

        System.out.println(Arrays.toString(result));
    }

    @Test
    public void getListTest() {

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.getList(pageable);

        System.out.println("========================================");
        result.getContent().forEach(obj -> {
            System.out.println(Arrays.toString(obj));
        });
        System.out.println("========================================");
    }

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

        Tag tag3 = Tag.builder()
                        .name("Mlops").build();
        tagRepository.save(tag1);
        tagRepository.save(tag2);
        tagRepository.save(tag3);
    }

    @Test
    public void insertTags() {
        IntStream.rangeClosed(1,300).forEach(i -> {
            int tag = (int) (Math.random()*3) + 1;
            int bno = (int) (Math.random() * 100) +1;
            if( bno!=1) {
                BoardTagMap boardTagMap = BoardTagMap.builder()
                        .tag(Tag.builder().tagnum((long) tag).build())
                        .board(Board.builder().bno((long) bno).build()).build();
                boardTagMapRepository.save(boardTagMap);
            }
        });
    }

    @Test
    public void insertBoard() {
        boardRepository.deleteAll();
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
    public void insertLikes() {
        IntStream.rangeClosed(1,100).forEach(i -> {

            int likeCnt = (int) (Math.random()*6);

            boardRepository.updateQuery(likeCnt, (long) i);

        });
    }

//    @Test
//    public void testWithReviewCount() {
//        List<Object[]> result = boardRepository.getList();
//
//        for(Object[] arr : result) {
//            System.out.println(Arrays.toString(arr));
//        }
//    }
}