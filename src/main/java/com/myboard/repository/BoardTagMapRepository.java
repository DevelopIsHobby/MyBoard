package com.myboard.repository;

import com.myboard.entity.BoardTagMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardTagMapRepository extends JpaRepository<BoardTagMap, Long> {
//    select b.bno, b.title, b.content, c.name from inven.board_tag_map a inner join inven.board b on
//    a.board_bno = b.bno inner join inven.tag c on a.tag_tagnum = c.tagnum
    @Query(value="Select b.bno, b.title, b.content, c.name from BoardTagMap a inner join Board b on a.board=b inner join Tag c on a.tag = c where b.bno=:bno")
    List<Object[]> getBoardWithTag(@Param("bno") Long bno);
}
