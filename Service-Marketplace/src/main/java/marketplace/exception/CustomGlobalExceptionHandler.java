package marketplace.exception;

import marketplace.dto.ErrorDto;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException ex, WebRequest request)
    {
        Map<String, String> errors = new HashMap<>();

        List<ObjectError> errorObjects = ex.getBindingResult().getAllErrors();

        for (ObjectError errorObject : errorObjects)
        {
            FieldError fieldError = (FieldError) errorObject;
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        String path = request.getDescription(false).substring(4);

        ErrorDto error = ErrorDto.builder()
                .timestamp(LocalDateTime.now())
                .message("Field Validation Error")
                .errors(errors)
                .path(path)
                .exceptionStackTrace(ExceptionUtils.getStackTrace(ex))
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
