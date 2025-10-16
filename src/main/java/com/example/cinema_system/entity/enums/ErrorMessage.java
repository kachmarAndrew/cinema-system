package com.example.cinema_system.entity.enums;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    BAD_REQUEST(400, "error.bad_request"),
    UNAUTHORIZED(401, "error.unauthorized"),
    FORBIDDEN(403, "error.forbidden"),
    NOT_FOUND(404, "error.not_found"),
    METHOD_NOT_ALLOWED(405, "error.method_not_allowed"),
    CONFLICT(409, "error.conflict"),
    UNSUPPORTED_MEDIA_TYPE(415, "error.unsupported_media_type"),
    INTERNAL_SERVER_ERROR(500, "error.internal_server_error"),
    SERVICE_UNAVAILABLE(503, "error.service_unavailable"),
    UNKNOWN(0, "error.unknown");

    private final int errorCode;
    private final String messageKey;

    ErrorMessage(int errorCode, String messageKey) {
        this.errorCode = errorCode;
        this.messageKey = messageKey;
    }

    public static ErrorMessage fromCode(int errorCode) {
        for (ErrorMessage errorMessage : values()) {
            if (errorMessage.errorCode == errorCode) {
                return errorMessage;
            }
        }
        return UNKNOWN;
    }

}
