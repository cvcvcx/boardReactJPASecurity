package org.cvcvcx.board.service;

import org.cvcvcx.board.dto.BoardDTO;
import org.cvcvcx.board.dto.PageRequestDTO;
import org.cvcvcx.board.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceImplTest {

    @Autowired
    private BoardService service;

    @Test
    public void testRegister(){
        BoardDTO dto = BoardDTO.builder()
                               .title("Test.")
                               .content("content...")
                               .writerEmail("user55@aaa.com")
                               .build();
        Long bno = service.register(dto);
    }


    @Test
    public void testGet(){
        Long bno = 100L;
        BoardDTO boardDTO = service.get(bno);
        System.out.println(boardDTO);
    }


}