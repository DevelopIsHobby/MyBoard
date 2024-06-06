package com.myboard.repository;

import com.myboard.entity.Board;
import com.myboard.entity.BoardTagMap;
import com.myboard.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardTagMapRepository extends JpaRepository<BoardTagMap, Long> {
//    select b.bno, b.title, b.content, c.name from inven.board_tag_map a inner join inven.board b on
//    a.board_bno = b.bno inner join inven.tag c on a.tag_tagnum = c.tagnum
    @Query(value="Select b.bno, b.title, b.content, c.name from BoardTagMap a inner join Board b on a.board=b inner join Tag c on a.tag = c where b.bno=:bno")
    List<Object[]> getBoardWithTag(@Param("bno") Long bno);

    @Query("select btm from BoardTagMap btm where btm.board = :board and btm.tag.name = :name")
    BoardTagMap findBoardTagMapByBoardAndTag(@Param("board") Board board, @Param("name") String name);

    @Modifying
    @Transactional
    @Query(value="delete from BoardTagMap where board.bno=:bno")
    void deleteByBno(Long bno);

    @Modifying
    @Transactional
    @Query(value = "delete from Tag t where t not in (select btm.tag from BoardTagMap btm)")
    void deleteOrphanTags();
}
