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
    Board getBoardByBno(Long bno);
    Long register(BoardDTO boardDTO);

    BoardDTO getTags(BoardDTO boardDTO);

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);
    PageResultDTO<BoardDTO, Object[]> getListMyPage(PageRequestDTO pageRequestDTO);

    void updateCount(Board board, Boolean likeBoolean);

    BoardDTO getBoard(Long bno);

    void removeBoards(Long bno);

    void modifyBoards(BoardDTO boardDTO);

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

    // 대표 화면용
    default BoardDTO entityToDTO(Board board, Member member, BoardImage boardImage, Long replyCount){
        if(board.getLikeCount() == null) { board.setLikeCount(0);}
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

        if(boardImage != null) {
            BoardImageDTO boardImageDTO = BoardImageDTO.builder()
                    .uuid(boardImage.getUuid())
                    .imgName(boardImage.getImgName())
                    .path(boardImage.getPath())
                    .build();
            boardDTO.setImageDTO(boardImageDTO);
        }

        return boardDTO;
    }

    // 조회 화면용
    default BoardDTO entityToDTOWithImages(Board board, Member member, List<BoardImage> boardImages, Long replyCount){
        if(board.getLikeCount() == null) { board.setLikeCount(0);}
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


        if(boardImages != null) {
            List<BoardImageDTO> boardImageDTOList = boardImages.stream()
                    .map(boardImage -> {
                        if(boardImage != null) {
                            return BoardImageDTO.builder()
                                    .path(boardImage.getPath())
                                    .imgName(boardImage.getImgName())
                                    .uuid(boardImage.getUuid())
                                    .build();
                        } else {
                            return  BoardImageDTO.builder()
                                    .path(null)
                                    .imgName("no image")
                                    .uuid(null)
                                    .build();
                        }
                    }).collect(Collectors.toUnmodifiableList());

            boardDTO.setImageDTOList(boardImageDTOList);
        }

        return boardDTO;
    }
}
