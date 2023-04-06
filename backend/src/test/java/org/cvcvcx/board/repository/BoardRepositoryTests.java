package org.cvcvcx.board.repository;

import org.cvcvcx.board.dto.BoardDTO;
import org.cvcvcx.board.dto.BoardListContentDto;
import org.cvcvcx.board.dto.PageRequestDTO;
import org.cvcvcx.board.dto.PageResultDTO;
import org.cvcvcx.board.entity.Board;
import org.cvcvcx.board.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoards(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Member member = Member.builder().email("user"+i+"@aaa.com").build();
            Board board = Board.builder().title("제목..."+i).content("content..."+i).writer(member).build();
            boardRepository.save(board);
        });
    }

    @Test
    @Transactional
    public void testRead1(){
        Optional<Board> result = boardRepository.findById(100L);

        Board board = result.get();

        System.out.println("-------------------------------------");
        System.out.println(board);
        System.out.println(board.getWriter());
    }


    @Test
    public void testReadWithWriter(){
        Object result = boardRepository.getBoardWithWriter(100L);
        Object[] arr = (Object[])result;
        System.out.println("-----------------------------");
        System.out.println(Arrays.toString(arr));


    }
    @Test
    public void testReadWithWriterAndReplyCount(){
        Pageable pageable = PageRequest.of(2,10, Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.getBoardListWithReplyCount(pageable);
        result.get().forEach(row->{
            Object[] arr = (Object[]) row;
            System.out.println(Arrays.toString(arr));
        });
    }

    @Test
    public void testRead3(){
        Object result = boardRepository.getBoardByBno(100L);

        Object[] arr = (Object[])result;
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testSearch1(){
         boardRepository.search1();

    }
    @Test
    public void testSearchPage1(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());
        Page<BoardListContentDto> t = boardRepository.searchPage("t", "1", pageable);
        for (BoardListContentDto boardListContentDto : t) {
            System.out.println("boardListContentDto = " + boardListContentDto);
        }

    }
    @Test
    public void testSearchPage2(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending().and(Sort.by("title").ascending()));
        Page<BoardListContentDto> t = boardRepository.searchPage("t", "1", pageable);

    }



}
