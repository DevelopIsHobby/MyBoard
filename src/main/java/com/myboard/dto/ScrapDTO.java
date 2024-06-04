package com.myboard.dto;

import com.myboard.entity.Board;
import com.myboard.entity.Member;
import lombok.*;

@Getter
@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class ScrapDTO {
    private Board board;
    private Member member;
}
