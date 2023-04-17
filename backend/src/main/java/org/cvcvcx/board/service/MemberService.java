package org.cvcvcx.board.service;

import org.cvcvcx.board.dto.MemberDTO;

public interface MemberService {

    public Boolean checkIdExist(MemberDTO memberDTO);

    public void register(MemberDTO memberDTO);

    public String login(MemberDTO memberDTO);
    public void logout();
}
