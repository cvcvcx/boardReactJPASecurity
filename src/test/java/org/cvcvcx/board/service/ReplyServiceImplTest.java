package org.cvcvcx.board.service;

import org.cvcvcx.board.dto.ReplyDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ReplyServiceImplTest {

    @Autowired
    private ReplyService service;

    @Test
    public void testGetList(){
        Long bno = 58L;
        List<ReplyDTO> replayDTOList = service.getList(bno);
        replayDTOList.forEach(replyDTO -> System.out.println(replyDTO));
    }


}