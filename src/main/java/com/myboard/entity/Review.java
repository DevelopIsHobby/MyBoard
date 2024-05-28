package com.myboard.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"board", "reviewer"})
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
}
