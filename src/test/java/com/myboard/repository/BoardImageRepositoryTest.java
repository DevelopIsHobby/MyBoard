package com.myboard.repository;

import com.myboard.entity.Board;
import com.myboard.entity.BoardImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.stream.IntStream;
@SpringBootTest
class BoardImageRepositoryTest {
    @Autowired
    private BoardImageRepository boardImageRepository;

    @Test
    public void insertBoardImages() {
        IntStream.rangeClosed(2,100).forEach(i -> {
            int count = (int) (Math.random() * 5) + 1; // 1,2,3,4,5

            for(int j=0; j<count; j++) {
                BoardImage boardImage = BoardImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .board(Board.builder().bno((long) i).build())
                        .imgName("test"+j+".jpg")
                        .build();

                boardImageRepository.save(boardImage);
            }
        });
    }
}