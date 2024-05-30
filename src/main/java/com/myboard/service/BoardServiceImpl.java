package com.myboard.service;

import com.myboard.dto.BoardDTO;
import com.myboard.entity.Board;
import com.myboard.entity.BoardImage;
import com.myboard.entity.Member;
import com.myboard.repository.BoardImageRepository;
import com.myboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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


    @Override
    public Long register(BoardDTO boardDTO) {
        Map<String, Object> entityMap = dtoToEntity(boardDTO);
        Board board = (Board) entityMap.get("board");
        List<BoardImage> boardImageList = (List<BoardImage>) entityMap.get("imgList");

        boardRepository.save(board);

        boardImageList.forEach(boardImage -> {
            boardImageRepository.save(boardImage);
        });
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
    public List<BoardDTO> getList() {
        List<Object[]> result = boardRepository.getList();

        Function<Object[], BoardDTO> fn = (arr -> entityToDTO(
                (Board) arr[0],
                (Member) arr[1],
                (BoardImage) arr[2],
                (Long) arr[3]
        ));

        List<BoardDTO> dtoList = result.stream().map(fn).collect(Collectors.toList());

        return dtoList;
    }

    @Override
    public BoardDTO get(Long bno) {
        Object result = boardRepository.getBoardbyBno(bno);

        Object[] arr = (Object[]) result;

        return null;
    }
}
