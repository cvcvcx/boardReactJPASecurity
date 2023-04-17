package org.cvcvcx.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cvcvcx.board.dto.BoardDTO;
import org.cvcvcx.board.dto.BoardListContentDto;
import org.cvcvcx.board.dto.PageRequestDTO;
import org.cvcvcx.board.dto.PageResultDTO;
import org.cvcvcx.board.entity.Board;
import org.cvcvcx.board.entity.Member;
import org.cvcvcx.board.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{
    private final BoardRepository repository;
    @Override
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

        //[Board(bno=77, title=제목...77, content=content...77), Member(email=user77@aaa.com, password=1111, name=USER77), 2]
        //위 코드를 보면 첫 요소가 Board, 두번째 요소가 Member, 세번째 요소가 replyCount 가 된다.
        //entityToDTO 는 위의 Object[]의 형태를 BoardDTO의 형태로 전환한다.
//        Function<BoardListContentDto[],BoardDTO> fn = (en->entityToDTO((Board) en[0],(Member) en[1],(Long) en[2]));
//
        Page<BoardListContentDto> result = repository.
                searchPage(pageRequestDTO.getType(), pageRequestDTO.getKeyword(), pageRequestDTO.getPageable(Sort.by("bno")
                                                                                                                                                    .descending()));
//        Page<BoardListContentDto> result = repository.searchPage(
//                pageRequestDTO.getType(),
//                pageRequestDTO.getKeyword(),
//                pageRequestDTO.getPageable(Sort.by("bno").descending())
//        );
//
        return new PageResultDTO<>(result);
//            return null;
    }


    @Override
    public BoardDTO get(Long bno) {
        Object result = repository.getBoardByBno(bno);
        Object[] arr = (Object[]) result;
        return entityToDTO((Board)arr[0],(Member) arr[1],(Long) arr[2]);
    }

    @Override
    public void modify(BoardDTO dto) {
        Optional<Board> result = repository.findById(dto.getBno());

        if(result.isPresent()){
            Board entity = result.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
        }

    }

    @Override
    public void remove(Long bno) {
        repository.deleteById(bno);
    }

}
