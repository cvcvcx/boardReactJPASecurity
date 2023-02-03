package org.cvcvcx.board.service;

import org.cvcvcx.board.dto.BoardDTO;
import org.cvcvcx.board.dto.PageRequestDTO;
import org.cvcvcx.board.dto.PageResultDTO;
import org.cvcvcx.board.entity.Board;
import org.cvcvcx.board.entity.Member;

import java.time.format.DateTimeFormatter;

public interface BoardService {

    Long register(BoardDTO dto);

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    BoardDTO get(Long bno);

    void modify(BoardDTO dto);
    void remove(Long bno);

    default Board dtoToEntity(BoardDTO dto){
        Member member = Member.builder().email(dto.getWriterEmail()).build();
        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
        return board;
    }

    default BoardDTO entityToDTO(Board board,Member member,Long replyCount){
        DateTimeFormatter fomatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .formattedRegDate(board.getRegDate().format(fomatter))
                .regDate(board.getRegDate())
                .formattedModDate(board.getModDate().format(fomatter))
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue())
                .build();
        return boardDTO;

    }

}
