package com.myboard.dto;

import com.myboard.entity.BaseEntity;
import com.myboard.entity.Board;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO{
    private Long rno;
    private Long bno;
    private String email;
    private String text;
    private LocalDateTime regDate, modDate;
}
