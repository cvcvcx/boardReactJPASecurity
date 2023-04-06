package org.cvcvcx.board.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cvcvcx.board.dto.BoardListContentDto;

import org.cvcvcx.board.dto.QBoardListContentDto;
import org.cvcvcx.board.entity.Board;

import org.cvcvcx.board.entity.QBoard;
import org.cvcvcx.board.entity.QMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;

import static org.cvcvcx.board.entity.QBoard.board;
import static org.cvcvcx.board.entity.QMember.member;
import static org.cvcvcx.board.entity.QReply.reply;
import static org.springframework.util.StringUtils.hasText;


@Log4j2
@RequiredArgsConstructor
public class SearchBoardRepositoryImpl implements SearchBoardRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<BoardListContentDto> search1() {
        log.info("search....................................");

        List<BoardListContentDto> result = queryFactory
                      .select(new QBoardListContentDto(
                              board.bno, board.title, board.content,
                              member.name.as("writerName"),
                               reply.count()))
                      .from(board)
                      .leftJoin(member).on(board.writer.eq(member))
                      .leftJoin(reply).on(reply.board.eq(board))
                       .groupBy(board)
                      .fetch();

        log.info("....................................");
        log.info(result);
        log.info("....................................");

        return result;
    }

    @Override
    public Page<BoardListContentDto> searchPage(String type, String keyword, Pageable pageable) {
        log.info("searchPage..............................");

        JPAQuery<BoardListContentDto> query =
                queryFactory
                        .select(
                        new QBoardListContentDto(
                                board.bno,
                                board.title,
                                board.content,
                                member.name,
                                reply.count().as("replyCount")))
                        .from(board)
                        .leftJoin(board.writer,member)//
                        .leftJoin(reply)
                        .on(reply.board.eq(board))
                        .where(
                                board.bno.gt(0L).and(titleLike(type, keyword)).or(contentLike(type, keyword))
                                         .or(writerLike(type, keyword))
                        )
                        .orderBy(getOrderSpecifier(pageable.getSort()).stream()
                                                                      .toArray(OrderSpecifier[]::new))
                        .limit(pageable.getPageSize())
                        .offset(pageable.getOffset())
                        .groupBy(board.bno);


        List<BoardListContentDto> result = query.fetch();

        JPAQuery<Long> count = queryFactory
                .select(board.count())
                .from(board)
                .where(
                        board.bno.gt(0L)
                                 .and(titleLike(type, keyword))
                                 .or(contentLike(type, keyword))
                                 .or(writerLike(type, keyword))
                );

        //

        return PageableExecutionUtils.getPage(result, pageable,count::fetchOne );

    }

    private BooleanExpression titleLike(String type, String keyword) {
        if(type==null||keyword==null){
            return null;
        }
        if(type.contains("t")&& hasText(keyword)){
            return board.title.contains(keyword);
        }
        return null;
    }

    private BooleanExpression contentLike(String type, String keyword) {
        if(type==null||keyword==null){
            return null;
        }
        if(type.contains("c")&& hasText(keyword)){
            return board.content.contains(keyword);
        }
        return null;
    }

    private BooleanExpression writerLike(String type, String keyword) {
        if(type==null||keyword==null){
            return null;
        }
        if(type.contains("w")&& hasText(keyword)){
            return board.writer.name.contains(keyword);
        }
        return null;
    }

    private static List<OrderSpecifier> getOrderSpecifier(Sort sort) {;
        List<OrderSpecifier> orderSpecifierList = new ArrayList<>();
        sort.stream().forEach(order -> {
            //sort에는 여러개의 정렬기준이 들어갈 수 있기 때문에, forEach문으로 그 조건들을 다 가져온다.
            //Order direction은 내림차순인지 오름차순인지 정함 ex) Order.ASC, Order.DESC
            //prop은 어떤 항목을 기준으로 하는지 정함 ex) bno, title, content
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(Board.class,"board");
            OrderSpecifier orderSpecifier = new OrderSpecifier<>(direction, orderByExpression.get(prop));
            orderSpecifierList.add(orderSpecifier);
        });
        return orderSpecifierList;
    }

    private static void checkTypeAndKeyword(String keyword, QBoard board, QMember member, String type, BooleanBuilder conditionBuilder) {
            if(type.contains("t")){
                conditionBuilder.or(board.title.contains(keyword));
            }
            if(type.contains("w")){
                conditionBuilder.or(member.name.contains(keyword));
            }
            if(type.contains("c")){
                conditionBuilder.or(board.content.contains(keyword));
            }

    }
}
