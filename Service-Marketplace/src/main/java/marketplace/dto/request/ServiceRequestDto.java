package marketplace.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import marketplace.model.VendorModel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceRequestDto
{
    @NotNull(message = "Service Title Can Not Be Null")
    @NotEmpty(message = "Service Title Can Not Be Empty")
    private String serviceTitle;

    @NotNull(message = "Service Description Can Not Be Null")
    @NotEmpty(message = "Service Description Can Not Be Empty")
    private String serviceDescription;

    @NotNull(message = "Service Price Can Not Be Null")
    @NotEmpty(message = "Service Price Can Not Be Empty")
    private String servicePrice;

    private VendorModel vendor;
}
