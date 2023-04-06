package org.cvcvcx.board.repository.search;

import org.cvcvcx.board.dto.BoardListContentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchBoardRepository {
    List<BoardListContentDto> search1();

    Page<BoardListContentDto> searchPage(String type, String keyword, Pageable pageable);
}
