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

import java.util.List;
import java.util.Map;

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
    public List<Object[]> getTags(Long bno) {
        return boardRepository.getTagByBno(bno);
    }

    @Override
    public List<Object[]> getList() {
        return boardRepository.getList();
    }

    @Override
    public BoardDTO get(Long bno) {
        Object result = boardRepository.getBoardbyBno(bno);

        Object[] arr = (Object[]) result;

        return null;
    }
}
