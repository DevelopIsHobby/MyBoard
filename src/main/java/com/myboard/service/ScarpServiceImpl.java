package com.myboard.service;

import com.myboard.dto.ScrapDTO;
import com.myboard.entity.Board;
import com.myboard.entity.Member;
import com.myboard.entity.Scrap;
import com.myboard.repository.BoardRepository;
import com.myboard.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ScarpServiceImpl implements ScrapService {
    private final ScrapRepository scrapRepository;
    private final BoardRepository boardRepository;
    @Override
    @Transactional
    public void add(ScrapDTO scrapDTO) throws Exception{
        log.info("ScarpServiceImpl, add...........");
        Member member = scrapDTO.getMember();
        Board board = scrapDTO.getBoard();

        if(scrapRepository.existsByBoardAndMember(board, member)) {
            System.out.println("insertError!!");
            throw new Exception();
        }

        Scrap scrap = Scrap.builder()
                .board(board)
                .member(member)
                .build();
        board.setScrapped(true);
        boardRepository.updateIsScrapped(true, board.getBno());
        scrapRepository.save(scrap);
    }

    @Override
    @Transactional
    public void cancel(ScrapDTO scrapDTO) throws Exception {
        log.info("ScarpServiceImpl, cancel...........");
        Member member = scrapDTO.getMember();
        Board board = scrapDTO.getBoard();

        if(!scrapRepository.existsByBoardAndMember(board, member)) {
            System.out.println("cancelError!!");
            board.setScrapped(false);
            boardRepository.updateIsScrapped(false, board.getBno());
        }
        board.setScrapped(false);
        boardRepository.updateIsScrapped(false, board.getBno());
        scrapRepository.deleteScrapByBoardAndMember(board, member);
    }

    @Override
    public List<Object[]> getScrapLists() {
        return scrapRepository.findAllScraps();
    }
}
