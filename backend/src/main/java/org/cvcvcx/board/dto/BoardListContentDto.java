package org.cvcvcx.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Data
public class BoardListContentDto {
    private Long bno;
    private String title;
    private String content;
    private String writerName;

    private Long replyCount;

    @QueryProjection
    public BoardListContentDto(Long bno, String title, String content, String writerName, Long replyCount) {
        this.bno = bno;
        this.title = title;
        this.content = content;
        this.writerName = writerName;
        this.replyCount = replyCount;
    }
}
