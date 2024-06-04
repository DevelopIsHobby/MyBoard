package com.myboard.repository;

import com.myboard.entity.Board;
import com.myboard.entity.Member;
import com.myboard.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    boolean existsByBoardAndMember(Board board, Member member);

    @Query(value="select board, member from Scrap")
    List<Object[]> findAllScraps();

    @Modifying
    @Transactional
    @Query("delete from Scrap where board=:board and member=:member")
    void deleteScrapByBoardAndMember(@Param("board") Board board,@Param("member") Member member);
}
