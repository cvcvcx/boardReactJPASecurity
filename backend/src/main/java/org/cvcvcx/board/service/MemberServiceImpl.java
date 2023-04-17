package org.cvcvcx.board.service;

import lombok.RequiredArgsConstructor;
import org.cvcvcx.board.dto.MemberDTO;
import org.cvcvcx.board.entity.Member;
import org.cvcvcx.board.entity.MemberRole;
import org.cvcvcx.board.repository.MemberRepository;
import org.cvcvcx.board.security.JwtProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public Boolean checkIdExist(MemberDTO memberDTO) {
        Optional<Member> member = memberRepository.findByEmail(memberDTO.getEmail());
        return (member.isPresent());

    }

    @Override
    public void register(MemberDTO memberDTO) {
        Boolean validateDuplicatedId = checkIdExist(memberDTO);
        if(!validateDuplicatedId) {
            Member newMember = Member.builder()
                                     .email(memberDTO.getEmail())
                                     .name(memberDTO.getName())
                                     .password(passwordEncoder.encode(memberDTO.getPassword()))
                                     .phoneNumber(memberDTO.getPhoneNumber())
                                     .build();
            newMember.addMemberRole(MemberRole.USER);
            memberRepository.save(newMember);
        }else{
            throw new RuntimeException("member already exist");
        }

    }

    @Override
    public String login(MemberDTO memberDTO) {

        Member member = memberRepository.findByEmail(memberDTO.getEmail()).orElseThrow(() ->
                 new BadCredentialsException("잘못된 계정정보입니다."));
        if(!passwordEncoder.matches(memberDTO.getPassword(), member.getPassword())){
            throw new BadCredentialsException("잘못된 계정정보입니다.");
        }
        return jwtProvider.createToken(member.getEmail(),member.getRoleSet().stream().collect(Collectors.toList()));
    }

    @Override
    public void logout() {

    }
}
