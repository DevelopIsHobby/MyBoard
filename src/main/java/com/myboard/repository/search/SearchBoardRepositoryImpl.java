package com.myboard.repository.search;

import com.myboard.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {
    public SearchBoardRepositoryImpl() {
        super(Board.class); // 사용하려는 도메인
    }
    @Override
    @Transactional
    public Board search() {
        log.info("search.............");
        QBoard board = QBoard.board;
        QBoardImage boardImage = QBoardImage.boardImage;
        QMember member = QMember.member;
        QReview review = QReview.review;
        QBoardTagMap boardTagMap = QBoardTagMap.boardTagMap;
        QTag tag = QTag.tag;

        JPQLQuery<Board> jpqlQuery = from(board);


        jpqlQuery.leftJoin(board.writer, member);
        jpqlQuery.leftJoin(boardImage).on(boardImage.board.eq(board));
        jpqlQuery.leftJoin(review).on(review.board.eq(board));
        jpqlQuery.leftJoin(boardTagMap).on(boardTagMap.board.eq(board));
        jpqlQuery.leftJoin(tag).on(boardTagMap.tag.eq(tag));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(
                board, member.email, boardImage, boardTagMap, tag.name, review.countDistinct()
        );

        tuple.groupBy(board);

        log.info("=====================");
        log.info(jpqlQuery);
        log.info("=====================");

        List<Tuple> result = tuple.fetch();

        log.info(result);

        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        log.info("SearchPage........................");

        QBoard board = QBoard.board;
        QBoardImage boardImage = QBoardImage.boardImage;
        QBoardImage boardImageSub = new QBoardImage("boardImageSub");
        QReview review = QReview.review;
        QMember member = QMember.member;


        JPQLQuery<Board> jpqlQuery = from(board);

        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(boardImage).on(boardImage.board.eq(board)
                .and(boardImage.inum.eq(
                        JPAExpressions.select(boardImageSub.inum.min())
                                .from(boardImageSub)
                                .where(boardImageSub.board.eq(board))
                )));
        jpqlQuery.leftJoin(review).on(review.board.eq(board));


        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, boardImage, review.countDistinct());


        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0l);

        booleanBuilder.and(expression);

        if(type!=null) {
            String[] typeArr = type.split("");
            //검색 조건을 작성하기
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for(String t : typeArr) {
                switch (t) {
                    case "t" :
                        conditionBuilder.or(board.title.contains(keyword));
                        break;
                    case "w" :
                        conditionBuilder.or(member.email.contains(keyword));
                        break;
                    case "c" :
                        conditionBuilder.or(board.content.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }
        tuple.where(booleanBuilder);

        tuple.groupBy(board, member, boardImage);

        this.getQuerydsl().applyPagination(pageable, tuple); // limit, offset, sort적용

        List<Tuple> result = tuple.fetch(); // fetch()는 쿼리 실행 결과를 가져온다.

        log.info(result);

        long count = tuple.fetchCount();

        log.info("Count : " + count);

        return new PageImpl<Object[]>(result.stream().map(Tuple::toArray).collect(Collectors.toList()),pageable, count);
    }

    @Override
    public Page<Object[]> findBoardsByTagName(String tagName) {
        log.info("findBoardsByTagName........................");

        QBoard board = QBoard.board;
        QBoardTagMap boardTagMap = QBoardTagMap.boardTagMap;
        QTag tag = QTag.tag;

        JPQLQuery<Board> tagJpqlQuery = from(board);

        tagJpqlQuery.leftJoin(boardTagMap).on(boardTagMap.board.eq(board));
        tagJpqlQuery.leftJoin(tag).on(boardTagMap.tag.eq(tag));
        tagJpqlQuery.where(tag.name.contains(tagName));

        List<Board> boards = tagJpqlQuery.select(board).fetch();
//        System.out.println("boards = " + boards);

        if (boards != null && !boards.isEmpty()) {
            Pageable pageable = PageRequest.of(0, 9, Sort.by("bno").descending().and(Sort.by("title").ascending()));

            QBoardImage boardImage = QBoardImage.boardImage;
            QBoardImage boardImageSub = new QBoardImage("boardImageSub");
            QReview review = QReview.review;
            QMember member = QMember.member;

            JPQLQuery<Board> jpqlQuery = from(board);

            jpqlQuery.leftJoin(member).on(board.writer.eq(member));
            jpqlQuery.leftJoin(boardImage).on(boardImage.board.eq(board)
                    .and(boardImage.inum.eq(
                            JPAExpressions.select(boardImageSub.inum.min())
                                    .from(boardImageSub)
                                    .where(boardImageSub.board.eq(board))
                    )));
            jpqlQuery.leftJoin(review).on(review.board.eq(board));
            jpqlQuery.leftJoin(boardTagMap).on(boardTagMap.board.eq(board));
            jpqlQuery.leftJoin(tag).on(boardTagMap.tag.eq(tag));
            jpqlQuery.where(tag.name.contains(tagName));

            JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, boardImage, review.countDistinct());

            tuple.where(board.in(boards));

            tuple.groupBy(board, member, boardImage);

            this.getQuerydsl().applyPagination(pageable, tuple); // limit, offset, sort 적용

            List<Tuple> result = tuple.fetch(); // fetch()는 쿼리 실행 결과를 가져온다.

            log.info(result);

            long count = tuple.fetchCount();

            log.info("Count : " + count);

            return new PageImpl<Object[]>(result.stream().map(Tuple::toArray).collect(Collectors.toList()), pageable, count);
        }

        return new PageImpl<Object[]>(Collections.emptyList());
    }

}
