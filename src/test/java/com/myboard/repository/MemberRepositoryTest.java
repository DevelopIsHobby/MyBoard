package com.myboard.repository;

import com.myboard.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;
@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMember() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@aaa.com")
                    .password("1111")
                    .nickName("USER" +i)
                    .build();

            memberRepository.save(member);
        });
    }

    @Test
    public void findMember() {
        String member56_email = memberRepository.findByEmail("user56@aaa.com");
        System.out.println(member56_email);
    }
}