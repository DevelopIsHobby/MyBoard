package com.myboard.repository;

import com.myboard.entity.Board;
import com.myboard.entity.Member;
import com.myboard.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    boolean existsByBoardAndWriter(Board board, Member writer);

    @Query(value="select r.board.bno, r.writer.email from Recommendation r where r.board = :board and r.writer = :writer")
    List<Object[]> findByBoardAndMember(@Param("board") Board board, @Param("writer") Member writer);

    @Modifying
    @Transactional
    @Query("delete from Recommendation where board=:board and writer=:writer")
    void recommendationCancel(@Param("board") Board board,@Param("writer") Member writer);

    @Modifying
    @Query("delete from Recommendation where board.bno=:bno")
    void deleteByBno(Long bno);
}
