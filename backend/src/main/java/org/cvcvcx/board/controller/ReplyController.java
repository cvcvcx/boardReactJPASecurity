package org.cvcvcx.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cvcvcx.board.dto.AuthMemberDTO;
import org.cvcvcx.board.dto.ReplyDTO;
import org.cvcvcx.board.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/replies/")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    @GetMapping(value = "/board/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("bno") Long bno) {
        log.info("bno : " + bno);
        return new ResponseEntity<>(replyService.getList(bno), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO, @AuthenticationPrincipal AuthMemberDTO loginMember) {
        if (loginMember != null) {
            replyDTO.setReplyer(loginMember.getUsername());
            log.info(replyDTO);
            Long rno = replyService.register(replyDTO);
            return new ResponseEntity<>(rno, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@RequestBody ReplyDTO replyDTO, @AuthenticationPrincipal AuthMemberDTO loginMember) {
        if (loginMember != null) {
            replyDTO.setReplyer(loginMember.getUsername());
            log.info(replyDTO);
            replyService.modify(replyDTO);
            return new ResponseEntity<>("success", HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping(value = "/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno,@AuthenticationPrincipal AuthMemberDTO loginMember) {
        log.info("rno : " + rno);
        replyService.remove(rno,loginMember);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }


}
