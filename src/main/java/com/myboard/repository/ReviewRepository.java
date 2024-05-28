package com.myboard.repository;

import com.myboard.entity.Board;
import com.myboard.entity.Member;
import com.myboard.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @EntityGraph(attributePaths = {"reviewer"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByBoard(Board board);

    @Modifying
    @Transactional
    @Query("delete from Review mr where mr.reviewer=:member and mr.board.bno=:bno")
    void deleteByMember(@Param("member") Member member, @Param("bno")Long bno);
}
