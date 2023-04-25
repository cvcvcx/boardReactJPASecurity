package org.cvcvcx.board.service;

import org.assertj.core.api.Assertions;
import org.cvcvcx.board.dto.MemberDTO;
import org.cvcvcx.board.exception.EmailDuplicateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceImplTest {

    @Autowired
    MemberService memberService;

    @Test
    public void 회원가입에러발생검증() {
        MemberDTO memberDTO = MemberDTO.builder()
                                       .email("user12314e1@naver.com")
                                       .password("asdfghj123#@!")
                                       .name("조창훈")
                                       .phoneNumber("01012341234")
                                       .build();
        memberService.register(memberDTO);
        assertThatThrownBy(()-> memberService.register(memberDTO)).isInstanceOf(EmailDuplicateException.class);
    }

    @Test
    public void 로그인에러발생검증() {
        MemberDTO memberDTO = MemberDTO.builder()
                                       .email("user12314e1@naver.com")
                                       .password("a12345678@!")
                                       .name("조창훈")
                                       .phoneNumber("01012341234")
                                       .build();
        memberService.register(memberDTO);
        memberDTO.setPassword("11111111");
        assertThatThrownBy(()->memberService.login(memberDTO)).isInstanceOf(BadCredentialsException.class);


    }

}