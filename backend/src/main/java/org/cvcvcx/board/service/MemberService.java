package org.cvcvcx.board.service;

import org.cvcvcx.board.dto.LoginResponseDTO;
import org.cvcvcx.board.dto.MemberDTO;

public interface MemberService {

    public Boolean checkIdExist(MemberDTO memberDTO);

    public void register(MemberDTO memberDTO);

    public LoginResponseDTO login(MemberDTO memberDTO);
    public void logout();
}
