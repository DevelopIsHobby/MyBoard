package com.myboard.repository;

import com.myboard.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, String> {
    @Query(value="select email from Member where email=:email")
    String findByEmail(@Param("email") String email);
}
