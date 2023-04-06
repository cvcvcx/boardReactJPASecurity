package org.cvcvcx.board.repository;

import org.cvcvcx.board.dto.MemberDTO;
import org.cvcvcx.board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,String> {
    public Member findByEmail(String email);

    public Member findByEmailAndPassword(String email,String password);
}
