package org.cvcvcx.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberDTO {
    @NotEmpty(message = "이메일은 필수입니다.")
    @Email
    private String email;

    @Min(10)
    private String password;

    private String name;

    private String phoneNumber;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

}
