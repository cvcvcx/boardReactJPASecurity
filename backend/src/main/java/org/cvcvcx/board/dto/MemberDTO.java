package org.cvcvcx.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberDTO {
    private String email;

    private String password;

    private String name;

    private String phoneNumber;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

}
