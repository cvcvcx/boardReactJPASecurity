package org.cvcvcx.board.repository;

import org.cvcvcx.board.dto.MemberDTO;
import org.cvcvcx.board.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,String> {
    @EntityGraph(attributePaths = {"roleSet"})
    public Optional<Member> findByEmail(String email);

}
