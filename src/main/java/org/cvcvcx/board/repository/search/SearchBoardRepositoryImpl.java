package org.cvcvcx.board.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.cvcvcx.board.entity.Board;
import org.cvcvcx.board.entity.QBoard;
import org.cvcvcx.board.entity.QMember;
import org.cvcvcx.board.entity.QReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;


@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository{

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Board search1() {
        log.info("search....................................");

        QBoard board = QBoard.board;
        QMember member =QMember.member;
        QReply reply = QReply.reply;

        JPQLQuery<Board> jpqlQuery = from(board);

        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        JPQLQuery<Tuple> tupleJPQLQuery = jpqlQuery.select(board, member.email, reply.count());
        tupleJPQLQuery.groupBy(board);

        log.info("....................................");
        log.info(tupleJPQLQuery);
        log.info("....................................");
        List<Tuple> result = tupleJPQLQuery.fetch();
        log.info(result);
        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        log.info("searchPage..............................");

        QBoard board = QBoard.board;
        QMember member =QMember.member;
        QReply reply = QReply.reply;


        JPQLQuery<Board> jpqlQuery = from(board);

        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        JPQLQuery<Tuple> tupleJPQLQuery = jpqlQuery.select(board, member, reply.count());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0L);

        booleanBuilder.and(expression);

        if(type!=null){
            BooleanBuilder conditionBuilder = new BooleanBuilder();
            checkTypeAndKeyword(keyword, board, member, type, conditionBuilder);
            booleanBuilder.and(conditionBuilder);
        }
        tupleJPQLQuery.where(booleanBuilder);

        //sort를  com.querydsl.core.types.OrderSpecifier<T>타입으로 바꿔줘야함
        //tupleJPQLQuery.orderBy(board.bno.desc());<===여기의 파라미터로 써먹기 위해서임
        //pageable에서 주어진 Sort를 가져옴
        Sort sort = pageable.getSort();

        sort.stream().forEach(order -> {
            //sort에는 여러개의 정렬기준이 들어갈 수 있기 때문에, forEach문으로 그 조건들을 다 가져온다.
            //Order direction은 내림차순인지 오름차순인지 정함 ex) Order.ASC, Order.DESC
            //prop은 어떤 항목을 기준으로 하는지 정함 ex) bno, title, content
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(Board.class,"board");
            tupleJPQLQuery.orderBy(new OrderSpecifier<>(direction,orderByExpression.get(prop)));
        });

        tupleJPQLQuery.groupBy(board);

        tupleJPQLQuery.offset(pageable.getOffset());
        tupleJPQLQuery.limit(pageable.getPageSize());

        List<Tuple> result = tupleJPQLQuery.fetch();

        log.info(result);

        long count = tupleJPQLQuery.fetchCount();

        log.info("COUNT : "+count);

        return new PageImpl<Object[]>(result.stream().map(t-> t.toArray()).collect(Collectors.toList()),pageable,count);

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
