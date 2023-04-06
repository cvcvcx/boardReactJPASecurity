package org.cvcvcx.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cvcvcx.board.dto.BoardDTO;
import org.cvcvcx.board.dto.BoardListContentDto;
import org.cvcvcx.board.dto.PageRequestDTO;
import org.cvcvcx.board.dto.PageResultDTO;
import org.cvcvcx.board.service.BoardService;
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
    public String registerPost(@RequestBody BoardDTO dto){
        log.info("컨텐츠 등록..."+dto);
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
    public String modifyPost(@RequestBody BoardDTO dto) {
        log.info(dto);
        boardService.modify(dto);
        return "update";
    }

    @PostMapping("/delete")
    public String deletePost(long id) {
        log.info(id);
        boardService.remove(id);
        return "delete";
    }
}
