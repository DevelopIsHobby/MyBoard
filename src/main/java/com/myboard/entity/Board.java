package com.myboard.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude="writer")
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Member writer;

    private Integer likeCount=0;

    private boolean isScrapped;

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }
    public void setScrapped(boolean isScrapped) { this.isScrapped = isScrapped; }
}
