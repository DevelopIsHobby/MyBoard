package com.myboard.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@Data
public class LikeRequestDTO {
    private String member_email;
    private Long board_bno;

    public LikeRequestDTO(String member_email, Long board_bno) {
        this.member_email = member_email;
        this.board_bno = board_bno;
    }
}
