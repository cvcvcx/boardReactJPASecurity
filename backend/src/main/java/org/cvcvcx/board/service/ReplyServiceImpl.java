package org.cvcvcx.board.service;

import lombok.RequiredArgsConstructor;
import org.cvcvcx.board.dto.AuthMemberDTO;
import org.cvcvcx.board.dto.ReplyDTO;
import org.cvcvcx.board.entity.Board;
import org.cvcvcx.board.entity.Member;
import org.cvcvcx.board.entity.Reply;
import org.cvcvcx.board.repository.MemberRepository;
import org.cvcvcx.board.repository.ReplyRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository repository;
    private final MemberRepository memberRepository;

    @Override
    public Long register(ReplyDTO replyDTO) {
        Reply reply = dtoToEntity(replyDTO);
        repository.save(reply);

        return reply.getRno();
    }

    @Override
    @Transactional
    public List<ReplyDTO> getList(Long bno) {
        List<Reply> result = repository.getRepliesByBoardOrderByRno(Board.builder()
                                                                         .bno(bno)
                                                                         .build());

        return result.stream()
                     .map(reply -> {
                         return entityToDTO(reply);
                     })
                     .collect(Collectors.toList());
    }

    @Override
    public void modify(ReplyDTO replyDTO) {
        Optional<Reply> reply = repository.findById(replyDTO.getRno());
        if (reply.isPresent()) {
            Member replyer = reply.get()
                                  .getReplyer();
            Optional<Member> loginMember = memberRepository.findByEmail(replyDTO.getReplyer());
            if (loginMember.isEmpty()) {
                throw new BadCredentialsException("수정권한이 없습니다.");
            } else if (loginMember.get() != replyer) {
                throw new BadCredentialsException("수정권한이 없습니다.");
            } else {
                Reply replyEntity = dtoToEntity(replyDTO);
                repository.save(replyEntity);
            }
        } else {
            throw new NullPointerException("댓글이 존재하지 않습니다.");
        }
    }

    @Override
    public void remove(Long rno, AuthMemberDTO loginMember) {
        Optional<Reply> result = repository.findById(rno);

        if (result.isPresent()) {
            Member writer = result.get()
                                  .getReplyer();

            Optional<Member> loginedMember = memberRepository.findByEmail(loginMember.getUsername());
            if (loginedMember.isEmpty()) {
                throw new BadCredentialsException("삭제 권한이 없습니다.");
            } else if (loginedMember.get() != writer) {
                throw new BadCredentialsException("삭제 권한이 없습니다.");
            }
            repository.deleteById(rno);
        }
    }
}
