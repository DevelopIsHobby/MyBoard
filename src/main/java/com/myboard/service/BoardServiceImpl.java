package com.myboard.service;

import com.myboard.dto.BoardDTO;
import com.myboard.dto.PageRequestDTO;
import com.myboard.dto.PageResultDTO;
import com.myboard.entity.*;
import com.myboard.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final BoardImageRepository boardImageRepository;
    private final MemberRepository memberRepository;
    private final BoardTagMapRepository boardTagMapRepository;
    private final TagRepository tagRepository;
    private final RecommendationRepository recommendationRepository;
    private final ReviewRepository reviewRepository;
    private final ScrapRepository scrapRepository;

    @Override
    public Board getBoardByBno(Long bno) {
        return boardRepository.getOnlyBoardByBno(bno);
    }

    @Override
    public Long register(BoardDTO boardDTO) {
        Map<String, Object> entityMap = dtoToEntity(boardDTO);
        Board board = (Board) entityMap.get("board");
        List<BoardImage> boardImageList = (List<BoardImage>) entityMap.get("imgList");

        Member writer = Member.builder()
                .email(boardDTO.getWriterEmail())
                        .password("1234")
                                .nickName(boardDTO.getWriterEmail()).build();
        memberRepository.save(writer);

        boardRepository.save(board);

        if(boardDTO.getTags() != null) {
            Tag tag = Tag.builder().name(boardDTO.getTags().stream().collect(Collectors.joining("#"))).build();
            tagRepository.save(tag);

            BoardTagMap boardTagMap = BoardTagMap.builder()
                    .board(board)
                    .tag(tag)
                    .build();

            boardTagMapRepository.save(boardTagMap);
        }

        if(boardImageList != null) {
            boardImageList.forEach(boardImage -> {
                boardImageRepository.save(boardImage);
            });
        }


        return board.getBno();
    }

    @Override
    public BoardDTO getTags(BoardDTO boardDTO) {
        Object[] tagsEntity = boardRepository.getTagByBno(boardDTO.getBno());
        List<String> tagList = new ArrayList<>();
        for(Object tag : tagsEntity) {
            tagList.add((String) tag);
        }
        boardDTO.setTags(tagList);
        return boardDTO;
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
//        Pageable pageable = (Pageable) pageRequestDTO.getPageable(Sort.by("bno").descending());
//        Page<Object[]> result = boardRepository.getList(pageable);

        log.info("boardService..........pageRequestDTO");


        Function<Object[], BoardDTO> fn = (arr -> entityToDTO(
                (Board) arr[0],
                (Member) arr[1],
                (BoardImage) arr[2],
                (Long) arr[3]
        ));
        Page<Object[]> result = null;

        if(pageRequestDTO.getType().equals("tags")) {
            result = boardRepository.findBoardsByTagName(
                    pageRequestDTO.getKeyword()
            );
        } else {
            result = boardRepository.searchPage(
                    pageRequestDTO.getType(),
                    pageRequestDTO.getKeyword(),
                    pageRequestDTO.getPageable(Sort.by("bno").descending())
            );
        }
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getListMyPage(PageRequestDTO pageRequestDTO) {
        log.info("boardService..........getListMyPage");


        Function<Object[], BoardDTO> fn = (arr -> entityToDTO(
                (Board) arr[0],
                (Member) arr[1],
                (BoardImage) arr[2],
                (Long) arr[3]
        ));

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage()-1,
                pageRequestDTO.getSize(),
                Sort.by("bno").descending()
        );

        Page<Object[]> result = boardRepository.getListMyPage(pageable);


        return new PageResultDTO<>(result, fn);
    }

    @Override
    public void updateCount(Board board, Boolean likeBoolean) {
        log.info("boardService.....updateCount");
        System.out.println("board.getBno() = " + board.getBno());
        if(likeBoolean) {
            boardRepository.updateLikeCount(1, board.getBno());
        }else {
            boardRepository.updateLikeCount(-1, board.getBno());
        }
    }

    @Override
    public BoardDTO getBoard(Long bno) {
        List<Object[]> result = boardRepository.getBoardByBno(bno);

        Board board = (Board) result.get(0)[0];
        Member member = (Member) result.get(0)[1];

        List<BoardImage> boardImageList = new ArrayList<>();
        result.forEach(arr -> {
            BoardImage boardImage = (BoardImage) arr[2];
            boardImageList.add(boardImage);
        });

        Long reviewCnt = (Long) result.get(0)[3];

        return entityToDTOWithImages(board, member, boardImageList, reviewCnt);
    }

    @Override
    @Transactional
    public void removeBoards(Long bno) {
        log.info("remveService.........");
        // 이미지
        boardImageRepository.deleteByBno(bno);
        // 태그 -> 태그
        boardTagMapRepository.deleteByBno(bno);
        boardTagMapRepository.deleteOrphanTags();
        // 추천
        recommendationRepository.deleteByBno(bno);
        // 리뷰
        reviewRepository.deleteByBno(bno);
        // 스크랩
        scrapRepository.deleteByBno(bno);

        // 게시판
        boardRepository.deleteByBno(bno);
    }

    @Override
    @Transactional
    public void modifyBoards(BoardDTO boardDTO) {
        Map<String, Object> entityMap = dtoToEntity(boardDTO);
        Board board = (Board) entityMap.get("board");
        List<BoardImage> boardImageList = (List<BoardImage>) entityMap.get("imgList");

        boardRepository.save(board);


        if(boardDTO.getTags() != null) {
            Tag tag = Tag.builder().name(boardDTO.getTags().stream().collect(Collectors.joining("#"))).build();

            tagRepository.save(tag);
            BoardTagMap boardTagMap = BoardTagMap.builder()
                    .board(board)
                    .tag(tag)
                    .build();

            boardTagMapRepository.deleteByBno(board.getBno());
            boardTagMapRepository.save(boardTagMap);
        }

        if(boardImageList != null) {
            boardImageRepository.deleteByBno(board.getBno());
            boardImageList.forEach(boardImage -> {
                boardImageRepository.save(boardImage);
            });
        }
    }
}
