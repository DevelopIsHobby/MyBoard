package com.myboard.repository;

import com.myboard.entity.Board;
import com.myboard.entity.Like;
import com.myboard.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Modifying
    @Query(value="insert into like (board, writer) values (:bno, :writer)", nativeQuery=true)
    int like(@Param("bno") Long bno,@Param("writer") String writer);

    @Modifying
    @Query(value="delete from like where board = :bno and writer = :writer", nativeQuery = true)
    int cancelLike(@Param("bno") Long bno,@Param("writer") String writer);

}
