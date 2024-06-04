package com.myboard.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"writer", "board"})
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long linum;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
}
