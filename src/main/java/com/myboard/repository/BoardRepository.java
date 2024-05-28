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
            "group by b, bi, w order by b.bno")
    List<Object[]> getList();

    @Query(value="select bt.board.bno, t.name from BoardTagMap bt " +
            "left join bt.tag t " +
            "where bt.board.bno =:bno group by bt.board.bno, t.name order by bt.board.bno")
    List<Object[]> getTagByBno(@Param("bno") Long bno);

    @Query(value = "select b, w,bi, count(distinct r) from Board b " +
            "left join b.writer w " +
            "left join BoardImage bi on b=bi.board " +
            "left join Review r on r.board = b " +
            "where bi.inum = (select min(bi2.inum) from BoardImage bi2 where bi2.board=b) " +
            "and b.bno=:bno group by b, bi, w order by b.bno")
    List<Object[]> getBoardbyBno(@Param("bno") Long bno);


    @Modifying
    @Transactional
    @Query(value="update board set like_count=:boardCount where bno=:bno", nativeQuery=true)
    int updateQuery(@Param("boardCount") int boardCount, @Param("bno")long bno);

}
