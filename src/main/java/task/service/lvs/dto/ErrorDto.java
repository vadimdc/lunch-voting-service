package task.service.lvs.dto;

import java.io.Serializable;

public class ErrorDto implements Serializable
{
    private ResponseErrorCode errorCode;
    private String message;

    public ErrorDto(ResponseErrorCode errorCode, String message)
    {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ResponseErrorCode getErrorCode()
    {
        return errorCode;
    }

    public String getMessage()
    {
        return message;
    }
}
