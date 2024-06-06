package com.myboard.repository.search;

import com.myboard.entity.Board;
import com.myboard.entity.QBoard;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {
    public SearchBoardRepositoryImpl() {
        super(Board.class); // 사용하려는 도메인
    }
    @Override
    public Board search() {
        log.info("search.............");
        QBoard board = QBoard.board;
        JPQLQuery<Board> jpqlQuery = from(board);

        jpqlQuery.select(board).where(board.bno.eq(11l));

        log.info("=====================");
        log.info(jpqlQuery);
        log.info("=====================");

        List<Board> result = jpqlQuery.fetch();



        return null;
    }
}
