package com.myboard.service;

import com.myboard.dto.BoardDTO;
import com.myboard.dto.BoardImageDTO;
import com.myboard.dto.PageRequestDTO;
import com.myboard.dto.PageResultDTO;
import com.myboard.entity.Board;
import com.myboard.entity.BoardImage;
import com.myboard.entity.Member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface BoardService {
    Long register(BoardDTO boardDTO);

    BoardDTO getTags(BoardDTO boardDTO);

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    void updateCount(Board board, Boolean likeBoolean);

    BoardDTO get(Long bno);

    // Map 타입으로 반환
    default Map<String, Object> dtoToEntity(BoardDTO boardDTO) {
        Map<String, Object> entitypMap = new HashMap<>();

        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(Member.builder().email(boardDTO.getWriterEmail()).build())
                .likeCount(boardDTO.getLikeCount())
                .isScrapped(boardDTO.isScrapped())
                .build();

        entitypMap.put("board", board);

        List<BoardImageDTO> imageDTOList = boardDTO.getImageDTOList();

        // BoardImageDTO 처리
        if(imageDTOList != null && imageDTOList.size() >0) {
            List<BoardImage> boardImageList = imageDTOList.stream().map(boardImageDTO -> {
                BoardImage boardImage = BoardImage.builder()
                        .path(boardImageDTO.getPath())
                        .imgName(boardImageDTO.getImgName())
                        .uuid(boardImageDTO.getUuid())
                        .board(board)
                        .build();
                return boardImage;
            }).collect(Collectors.toList());
            entitypMap.put("imgList", boardImageList);
        }


        return entitypMap;
    }

    default BoardDTO entityToDTO(Board board, Member member, BoardImage boardImage, Long replyCount){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .likeCount(board.getLikeCount())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .replyCount(replyCount.intValue())
                .isScrapped(board.isScrapped())
                .build();

        BoardImageDTO boardImageDTO = BoardImageDTO.builder()
                                .uuid(boardImage.getUuid())
                                .imgName(boardImage.getImgName())
                                        .path(boardImage.getPath())
                                                .build();

        boardDTO.setImageDTO(boardImageDTO);

        return boardDTO;
    }
}
