package marketplace.dto.response;

import lombok.*;
import marketplace.model.VendorModel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceResponseDto
{
    private long id;
    private String serviceTitle;
    private String serviceDescription;
    private String servicePrice;
    private String serviceStatus;
    private VendorModel vendor;
}
