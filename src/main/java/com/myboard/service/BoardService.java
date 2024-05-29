package com.myboard.service;

import com.myboard.dto.BoardDTO;
import com.myboard.dto.BoardImageDTO;
import com.myboard.dto.BoardTagDTO;
import com.myboard.entity.Board;
import com.myboard.entity.BoardImage;
import com.myboard.entity.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface BoardService {
    Long register(BoardDTO boardDTO);

    List<Object[]> getTags(Long bno);

    List<Object[]> getList();

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

    default BoardDTO entityToDTO(Board board, Member member, List<BoardImage> boardImages, Long replyCount){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .likeCount(board.getLikeCount())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .replyCount(replyCount.intValue())
                .build();

        List<BoardImageDTO> boardImageDTOList = boardImages.stream()
                .map(boardImage -> {
                    return BoardImageDTO.builder().imgName(boardImage.getImgName())
                            .path(boardImage.getPath())
                            .uuid(boardImage.getUuid())
                            .build();
                }).collect(Collectors.toList());



        boardDTO.setImageDTOList(boardImageDTOList);

        return boardDTO;
    }
}
