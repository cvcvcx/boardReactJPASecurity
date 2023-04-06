package org.cvcvcx.board.service;

import lombok.RequiredArgsConstructor;
import org.cvcvcx.board.dto.MemberDTO;
import org.cvcvcx.board.entity.Member;
import org.cvcvcx.board.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public String checkIdExist(MemberDTO memberDTO) {
        Member member = memberRepository.findByEmail(memberDTO.getEmail());
        if(member==null){
            return "y";
        }else {
        return "n";
        }
    }

    @Override
    public void register(MemberDTO memberDTO) {
        Member newMember = Member.builder()
                             .email(memberDTO.getEmail())
                             .name(memberDTO.getName())
                             .password(memberDTO.getPassword())
                             .phoneNumber(memberDTO.getPhoneNumber())
                             .build();
        memberRepository.save(newMember);
    }

    @Override
    public void login(MemberDTO memberDTO) {

    }

    @Override
    public void logout() {

    }
}
