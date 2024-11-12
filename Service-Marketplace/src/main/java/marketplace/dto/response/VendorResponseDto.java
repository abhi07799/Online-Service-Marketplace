package marketplace.dto.response;

import lombok.*;
import marketplace.model.ServiceModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendorResponseDto
{
    private long id;
    private String fullName;
    private String vendorMail;
    private String password;
    private String vendorAddress;
    private List<ServiceModel> services;
    private String role;
    private LocalDateTime AccountCreatedOn;
}
