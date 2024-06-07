package com.myboard.service;

import com.myboard.dto.LikeRequestDTO;
import com.myboard.entity.Board;
import com.myboard.entity.Member;
import com.myboard.entity.Recommendation;
import com.myboard.repository.BoardRepository;
import com.myboard.repository.MemberRepository;
import com.myboard.repository.RecommendationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{
    private final RecommendationRepository recommendationRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    @Override
    public void insert(LikeRequestDTO likeRequestDTO) throws Exception{
        log.info("LikeSerivceImpl, insert...........");
        Member member = Member.builder().email(memberRepository.findByEmail(likeRequestDTO.getMember_email())).build();
        Board board = Board.builder().bno((long) boardRepository.findByBno(likeRequestDTO.getBoard_bno())).build();

        System.out.println("exists board, member " + recommendationRepository.existsByBoardAndWriter(board, member));
        if(recommendationRepository.existsByBoardAndWriter(board, member)) {
            System.out.println("insertError!!");
            throw new Exception();
        }

        Recommendation recommendation = Recommendation.builder()
                .board(board)
                .writer(member)
                .build();

        recommendationRepository.save(recommendation);
    }

    @Transactional
    @Override
    public void delete(LikeRequestDTO likeRequestDTO) throws Exception {
        log.info("LikeServiceImpl, delete...........");
        Member member = Member.builder().email(memberRepository.findByEmail(likeRequestDTO.getMember_email())).build();

        Board board = Board.builder().bno((long) boardRepository.findByBno(likeRequestDTO.getBoard_bno())).build();
        System.out.println("not exists board, member " + recommendationRepository.existsByBoardAndWriter(board, member));

        if(!recommendationRepository.existsByBoardAndWriter(board, member)) {
            System.out.println("deleteError!!");
            throw new Exception();
        }
        recommendationRepository.recommendationCancel(board, member);
    }
}
