package org.cvcvcx.board.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND(404,"COMMON-ERR-404","PAGE NOT FOUND"),
    EMAIL_DUPLICATION(400,"MEMBER-ERR-400","EMAIL DUPLICATED"),
    BAD_CREDENTIAL(400,"MEMBER-ERR-400","BAD CREDENTIAL"),
    SERVER_ERROR(500,"COMMON-ERR-500","INTER SERVER ERROR")
    ;

    private final int status;
    private final String errorCode;
    private final String message;

}
