package task.service.lvs.controller;

import task.service.lvs.dto.ErrorDto;
import task.service.lvs.dto.ResponseErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Common class for handling exceptions
 */
@ControllerAdvice(assignableTypes = ApiController.class)
public class ApiExceptionHandler extends ApiController
{
    @ExceptionHandler({BindException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDto handleValidationException(Exception e)
    {
        logger.error(e);
        return new ErrorDto(ResponseErrorCode.VALIDATION_ERROR, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDto onValidationException(MethodArgumentNotValidException e)
    {
        return new ErrorDto(ResponseErrorCode.VALIDATION_ERROR, e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDto onGeneralException(Exception e)
    {
        logger.error(e);
        return new ErrorDto(ResponseErrorCode.GENERAL_ERROR, e.getMessage());
    }
}
