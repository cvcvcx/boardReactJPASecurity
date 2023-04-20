package org.cvcvcx.board.repository;

import org.cvcvcx.board.entity.Board;
import org.cvcvcx.board.entity.Reply;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.FetchType;
import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Long> {
    @Modifying
    @Query("delete from Reply r where r.board.bno =:bno")
    void deleteByBno(Long bno);

    @EntityGraph(attributePaths = {"replyer"})
    List<Reply> getRepliesByBoardOrderByRno(Board board);
}
