package com.myboard.repository;

import com.myboard.entity.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {
    @Modifying
    @Query(value="delete from BoardImage where board.bno=:bno")
    void deleteByBno(Long bno);
}
