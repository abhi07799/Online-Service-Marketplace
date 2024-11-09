package marketplace.exception;

import marketplace.dto.ErrorDto;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomGlobalExceptionHandler
{
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request)
    {
        String path = request.getDescription(false).substring(4);

        ErrorDto errorDto = ErrorDto.builder().timestamp(LocalDateTime.now()).message(ex.getMessage()).path(path).exceptionStackTrace(ExceptionUtils.getStackTrace(ex)).build();

        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ResourceAlreadyExistException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleResourceAlreadyExistException(ResourceAlreadyExistException ex, WebRequest request)
    {
        String path = request.getDescription(false).substring(4);

        ErrorDto errorDto = ErrorDto.builder().timestamp(LocalDateTime.now()).message(ex.getMessage()).path(path).exceptionStackTrace(ExceptionUtils.getStackTrace(ex)).build();

        return new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleAnyException(Exception ex, WebRequest request)
    {
        String path = request.getDescription(false).substring(4);

        ErrorDto errorDto = ErrorDto.builder().timestamp(LocalDateTime.now()).message(ex.getMessage()).path(path).exceptionStackTrace(ExceptionUtils.getStackTrace(ex)).build();

        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
