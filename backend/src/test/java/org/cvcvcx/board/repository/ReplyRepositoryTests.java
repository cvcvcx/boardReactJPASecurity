package org.cvcvcx.board.repository;

import org.cvcvcx.board.entity.Board;
import org.cvcvcx.board.entity.Member;
import org.cvcvcx.board.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Transactional
public class ReplyRepositoryTests {
    @Autowired
    private ReplyRepository replyRepository;


    @Test
    public void insertReply(){
        IntStream.rangeClosed(1,300).forEach(i->{
            long bno = 59L;
            Board board = Board.builder().bno(bno).build();
            Reply reply = Reply.builder()
                    .text("Reply..."+i)
                    .board(board)
                    .replyer(Member.builder().email("cvcvcx@naver.com").build())
                    .build();
            replyRepository.save(reply);
        });
    }



    @Test
    public void testReadReplyByBoard(){
        List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(59L).build());
        replyList.forEach(reply -> System.out.println(reply));
    }


}
