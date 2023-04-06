package org.cvcvcx.board.service;

import org.cvcvcx.board.dto.MemberDTO;

public interface MemberService {

    public String checkIdExist(MemberDTO memberDTO);

    public void register(MemberDTO memberDTO);

    public void login(MemberDTO memberDTO);
    public void logout();
}
