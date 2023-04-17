package org.cvcvcx.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cvcvcx.board.dto.*;
import org.cvcvcx.board.entity.Member;
import org.cvcvcx.board.service.BoardService;
import org.cvcvcx.board.service.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public PageResultDTO<BoardListContentDto> list(PageRequestDTO pageRequestDTO){
        log.info("pageRequestDTO"+pageRequestDTO);
        PageResultDTO<BoardListContentDto> list = boardService.getList(pageRequestDTO);
        return list;
    }

    @PostMapping("/register")
    public String registerPost(@RequestBody BoardDTO dto, @AuthenticationPrincipal AuthMemberDTO member){
        log.info("컨텐츠 등록..."+dto);
        dto.setWriterEmail(member.getUsername());
        Long register = boardService.register(dto);
        return register.toString();
    }
    @GetMapping("/read")
    public BoardDTO read(long id) {
        log.info("id: {}", id);
        BoardDTO dto = boardService.get(id);
        return dto;
    }

    @PostMapping("/modify")
    public String modifyPost(@RequestBody BoardDTO dto, @AuthenticationPrincipal AuthMemberDTO member) {
        log.info(dto);
        dto.setWriterEmail(member.getUsername());
        boardService.modify(dto);
        return "update";
    }

    @PostMapping("/delete")
    public String deletePost(long id, @AuthenticationPrincipal AuthMemberDTO member) {
        log.info(id);
        boardService.remove(id,member);
        return "delete";
    }
}
