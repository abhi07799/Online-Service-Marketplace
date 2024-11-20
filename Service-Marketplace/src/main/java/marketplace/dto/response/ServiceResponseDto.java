package marketplace.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import marketplace.model.VendorModel;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceResponseDto
{
    private long id;
    private String serviceTitle;
    private String serviceDescription;
    private String servicePrice;
    private String serviceStatus;
    private VendorModel vendor;
    private LocalDateTime createdAt;
}
