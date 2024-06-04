package com.myboard.repository;

import com.myboard.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query(value = "select b, w,bi, count(distinct r) from Board b " +
            "left join b.writer w " +
            "left join BoardImage bi on b=bi.board " +
            "left join Review r on r.board = b " +
            "where bi.inum = (select min(bi2.inum) from BoardImage bi2 where bi2.board=b)" +
            "group by b, bi, w")
    Page<Object[]> getList(Pageable pageable);

    @Query(value="select t.name from BoardTagMap bt " +
            "left join bt.tag t " +
            "where bt.board.bno =:bno group by t.name order by bt.board.bno")
    Object[] getTagByBno(@Param("bno") Long bno);

    @Query(value = "select b, w,bi, count(distinct r) from Board b " +
            "left join b.writer w " +
            "left join BoardImage bi on b=bi.board " +
            "left join Review r on r.board = b " +
            "where b.bno=:bno group by b, bi, w order by b.bno")
    List<Object[]> getBoardbyBno(@Param("bno") Long bno);


    @Modifying
    @Transactional
    @Query(value="update board set like_count=like_count + :boardCount where bno=:bno", nativeQuery=true)
    int updateLikeCount(@Param("boardCount") int boardCount, @Param("bno")long bno);

    @Query(value="select bno from Board where bno=:bno")
    Integer findByBno(@Param("bno") Long bno);
}
