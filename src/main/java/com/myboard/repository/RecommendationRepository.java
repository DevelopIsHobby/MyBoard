package com.myboard.repository;

import com.myboard.entity.Board;
import com.myboard.entity.Member;
import com.myboard.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    boolean existsByBoardAndWriter(Board board, Member writer);

    @Modifying
    @Transactional
    @Query("delete from Recommendation where board=:board and writer=:writer")
    void recommendationCancel(Board board, Member writer);
}
