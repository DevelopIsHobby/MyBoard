package com.myboard.repository;

import com.myboard.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query(value="select b, w, count(r) from Board b left join b.writer w left join Review r on r.board = b where b.bno=:bno group by b ")
    List<Object[]> getBoardWithReplyCount(@Param("bno") Long bno);
}
