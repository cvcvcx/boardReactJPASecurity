package org.cvcvcx.board.dto;


import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private Long bno;
    @NotEmpty
    @Size(min = 1,max = 100)
    private String title;
    @NotEmpty
    @Size(min = 1,max = 1000)
    private String content;
    private String writerEmail;

    private String writerName;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private String formattedRegDate;
    private String formattedModDate;

    private int replyCount;
}
