package org.cvcvcx.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cvcvcx.board.dto.LoginResponseDTO;
import org.cvcvcx.board.dto.MemberDTO;
import org.cvcvcx.board.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/checkEmail")
    public Boolean checkEmail(@RequestBody @Valid MemberDTO memberEmail){
        log.info(memberEmail.getEmail());
        return memberService.checkIdExist(memberEmail);
    }

    @PostMapping("/signup")
    public void register(@RequestBody @Valid MemberDTO memberDTO){
        log.info(memberDTO.toString());
        memberService.register(memberDTO);
    }
    @PostMapping("/signin")
    public LoginResponseDTO login(@RequestBody MemberDTO memberDTO){
        log.info(memberDTO.toString());
        return memberService.login(memberDTO);
    }

}
