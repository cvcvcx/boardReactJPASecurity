package org.cvcvcx.board.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cvcvcx.board.entity.Member;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyDTO {
    private Long rno;
    private String text;
    private String replyer;
    private Long bno;
    private LocalDateTime regDate,modDate;


}
