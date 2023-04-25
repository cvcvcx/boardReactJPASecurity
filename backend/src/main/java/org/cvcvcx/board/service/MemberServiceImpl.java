package org.cvcvcx.board.service;

import lombok.RequiredArgsConstructor;
import org.cvcvcx.board.dto.LoginResponseDTO;
import org.cvcvcx.board.dto.MemberDTO;
import org.cvcvcx.board.entity.Member;
import org.cvcvcx.board.entity.MemberRole;
import org.cvcvcx.board.exception.EmailDuplicateException;
import org.cvcvcx.board.exception.ErrorCode;
import org.cvcvcx.board.repository.MemberRepository;
import org.cvcvcx.board.security.JwtProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

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
        if (!validateDuplicatedId) {
            Member newMember = Member.builder()
                                     .email(memberDTO.getEmail())
                                     .name(memberDTO.getName())
                                     .password(passwordEncoder.encode(memberDTO.getPassword()))
                                     .phoneNumber(memberDTO.getPhoneNumber())
                                     .build();
            newMember.addMemberRole(MemberRole.USER);
            memberRepository.save(newMember);
        } else {
            throw new EmailDuplicateException("이메일이 이미 존재합니다.", ErrorCode.EMAIL_DUPLICATION);
        }

    }

    @Override
    public LoginResponseDTO login(MemberDTO memberDTO) {

        Member member = memberRepository.findByEmail(memberDTO.getEmail())
                                        .orElseThrow(() ->
                                                new BadCredentialsException("잘못된 계정정보입니다."));
        if (!passwordEncoder.matches(memberDTO.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("잘못된 계정정보입니다.");
        }
        return LoginResponseDTO.builder()
                               .userEmail(member.getEmail())
                               .userName(member.getName())
                               .accessToken(jwtProvider.createToken(member.getEmail(), member.getRoleSet()
                                                                                             .stream()
                                                                                             .collect(Collectors.toList())))
                               .build();
    }

    @Override
    public void logout() {

    }
}
