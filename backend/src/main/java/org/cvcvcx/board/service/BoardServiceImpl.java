package org.cvcvcx.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cvcvcx.board.dto.*;
import org.cvcvcx.board.entity.Board;
import org.cvcvcx.board.entity.Member;
import org.cvcvcx.board.repository.BoardRepository;
import org.cvcvcx.board.repository.MemberRepository;
import org.cvcvcx.board.repository.ReplyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {
    private final BoardRepository repository;
    private final MemberRepository memberRepository;
    private final ReplyRepository replyRepository;

    @Override
    @Transactional
    public Long register(BoardDTO dto) {
        log.info(dto);

        Board board = dtoToEntity(dto);

        repository.save(board);
        return board.getBno();
    }

    @Override
    @Transactional
    public PageResultDTO<BoardListContentDto> getList(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);


        Page<BoardListContentDto> result = repository.
                searchPage(pageRequestDTO.getType(), pageRequestDTO.getKeyword(), pageRequestDTO.getPageable(Sort.by("bno")
                                                                                                                 .descending()));

        return new PageResultDTO<>(result);
    }


    @Override
    @Transactional
    public BoardDTO get(Long bno) {
        Object result = repository.getBoardByBno(bno);
        Object[] arr = (Object[]) result;
        return entityToDTO((Board) arr[0], (Member) arr[1], (Long) arr[2]);
    }

    @Override
    @Transactional
    public void modify(BoardDTO dto) {

        Optional<Board> result = repository.findById(dto.getBno());

        if (result.isPresent()) {
            Member writer = result.get()
                                  .getWriter();
            Optional<Member> loginMember = memberRepository.findByEmail(dto.getWriterEmail());
            if (loginMember.isEmpty()) {
                throw new BadCredentialsException("수정 권한이 없습니다.");
            } else if (loginMember.get() != writer) {
                throw new BadCredentialsException("수정 권한이 없습니다.");
            }
            Board entity = result.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
        }

    }

    @Override
    @Transactional
    public void remove(Long bno, AuthMemberDTO loginMember) {
        Optional<Board> result = repository.findById(bno);

        if (result.isPresent()) {
            Member writer = result.get()
                                  .getWriter();
            Optional<Member> loginedMember = memberRepository.findByEmail(loginMember.getUsername());
            if (loginedMember.isEmpty()) {
                throw new BadCredentialsException("삭제 권한이 없습니다.");
            } else if (loginedMember.get() != writer) {
                throw new BadCredentialsException("삭제 권한이 없습니다.");
            }

            replyRepository.deleteByBno(bno);

            repository.deleteById(bno);
        }
    }

}
