package org.cvcvcx.board.service;

import org.cvcvcx.board.dto.AuthMemberDTO;
import org.cvcvcx.board.dto.ReplyDTO;
import org.cvcvcx.board.entity.Board;
import org.cvcvcx.board.entity.Member;
import org.cvcvcx.board.entity.Reply;

import java.util.List;

public interface ReplyService {
    Long register(ReplyDTO replyDTO);
    List<ReplyDTO> getList(Long bno);
    void modify(ReplyDTO replyDTO);
    void remove(Long rno, AuthMemberDTO loginMember);

    default Reply dtoToEntity(ReplyDTO replyDTO){
        Board board = Board.builder().bno(replyDTO.getBno()).build();

        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replyer(Member.builder().email(replyDTO.getReplyer()).build())
                .board(board)
                .build();
        return  reply;
    }

    default ReplyDTO entityToDTO(Reply reply){
        ReplyDTO dto = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer().getEmail())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
               .build();
        return dto;
    }
}
