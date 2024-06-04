package com.myboard.dto;

import com.myboard.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private Long bno;
    private String title;
    private String content;
    private String writerEmail;
    private int likeCount;
    private List<BoardImageDTO> imageDTOList = new ArrayList<>();
    private BoardImageDTO imageDTO;
    private List<String> tags = new ArrayList<>();
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private int replyCount;
    private boolean isScrapped;
}