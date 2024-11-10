package marketplace.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDto
{
    private LocalDateTime timestamp;
    private String message;
    private Map<String, String> errors;
    private String path;
    private String exceptionStackTrace;

}